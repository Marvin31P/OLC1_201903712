// Expresiones regulares
LineaTerminada = ";";
Numeral = "#";
Coma = ",";
LlaveA = "\\{";
LlaveC = "\\}";
ParentesisA = "\\(";
ParentesisC = "\\)";
Dolar = "\\$";
Guion = "-";
DosPuntos = ":";
MenorQue = "<";
MayorQue = ">";
Igual = "=";
Diagonal = "/";
Asterisco = "*";
Pipe = "|";
GuionBajo = "_";
Arroba = "@"; // Solo si se usa en los nombres

// Palabras reservadas y etiquetas
AP = "AP";
AFD = "AFD";
NOMBRE = "Nombre";
ESTADOS_ACEPTACION = "A";
ESTADOS_TRANSICIONES = "Transiciones";
ESTADO_INICIAL = "I";
ALFABETO = "T";
SIMBOLOS_PILA = "P";
VER_AUTOMATAS = "verAutomatas";
DESC = "desc";

// Tipos de datos
Letra = [a-zA-Z];
Digito = [0-9];
Simbolo = {Letra}|{Digito}|{GuionBajo};
ID = {Letra}({Simbolo})*;

// Otros tokens
Cadena = \"[^\"]*\"|'[^']*';
Flecha = "->";
ComentarioLinea = "//".*;
ComentarioMultilinea = "/*" [^*]* "*/" | "/*" [^*]* "*"+ [^*/] [^*]* "*/";

// Errores
Error = [^\\{\\}\\[\\]\\,\\;\\:\\(\\)\s<>=/|\\\"'\\*\\_a-zA-Z0-9$];

%%

// Reglas léxicas
{LineaTerminada} { return symbol(sym.PUNTO_COMA); }
{Numeral} { return symbol(sym.NUMERAL); }
{Coma} { return symbol(sym.COMA); }
{LlaveA} { return symbol(sym.LLAVE_A); }
{LlaveC} { return symbol(sym.LLAVE_C); }
{ParentesisA} { return symbol(sym.PARENTESIS_A); }
{ParentesisC} { return symbol(sym.PARENTESIS_C); }
{Dolar} { return symbol(sym.DOLAR); }
{Guion} { return symbol(sym.GUION); }
{DosPuntos} { return symbol(sym.DOS_PUNTOS); }
{MenorQue} { return symbol(sym.MENOR_QUE); }
{MayorQue} { return symbol(sym.MAYOR_QUE); }
{Igual} { return symbol(sym.IGUAL); }
{Diagonal} { return symbol(sym.DIAGONAL); }
{Asterisco} { return symbol(sym.ASTERISCO); }
{Pipe} { return symbol(sym.PIPE); }
{Flecha} { return symbol(sym.FLECHA); }

// Palabras reservadas y etiquetas
{AP} { return symbol(sym.AP); }
{AFD} { return symbol(sym.AFD); }
{NOMBRE} { return symbol(sym.NOMBRE); }
{ESTADOS_ACEPTACION} { return symbol(sym.A); }
{ESTADOS_TRANSICIONES} { return symbol(sym.TRANSICIONES); }
{ESTADO_INICIAL} { return symbol(sym.I); }
{ALFABETO} { return symbol(sym.T); }
{SIMBOLOS_PILA} { return symbol(sym.P); }
{VER_AUTOMATAS} { return symbol(sym.VER_AUTOMATAS); }
{DESC} { return symbol(sym.DESC); }

// Otros tokens
{Cadena} { return symbol(sym.CADENA, yytext().substring(1, yytext().length() - 1)); }
{ID} { return symbol(sym.ID, yytext()); }
{ComentarioLinea} { /* Ignorar el comentario */ }
{ComentarioMultilinea} { /* Ignorar el comentario */ }
{Error} { throw new Error("Carácter no válido: " + yytext() + " en línea " + (yyline + 1) + ", columna " + (yycolumn + 1)); }

// Espacios y saltos de línea
[ \t\r\n]+ { /* Ignorar espacios */ }
