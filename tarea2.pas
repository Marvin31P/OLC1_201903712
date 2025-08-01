program CRUD_Estudiantes;

uses crt;

type
  PEstudiante = ^TEstudiante;
  TEstudiante = record
    Nombre: string[30];
    Apellido: string[30];
    Edad: Integer;
    Direccion: string[50];
    Carrera: string[30];
    Curso: string[30];
    Estado: string[15];
    Sig: PEstudiante; 
  end;

var
  Lista: PEstudiante = nil;

procedure AgregarEstudiante;
var
  Nuevo, Actual: PEstudiante;
begin
  New(Nuevo);
  writeln('--- Agregar Estudiante ---');
  write('Nombre: '); readln(Nuevo^.Nombre);
  write('Apellido: '); readln(Nuevo^.Apellido);
  write('Edad: '); readln(Nuevo^.Edad);
  write('Direccion: '); readln(Nuevo^.Direccion);
  write('Carrera: '); readln(Nuevo^.Carrera);
  write('Curso: '); readln(Nuevo^.Curso);
  write('Estado: '); readln(Nuevo^.Estado);
  Nuevo^.Sig := nil;

  if Lista = nil then
    Lista := Nuevo
  else
  begin
    Actual := Lista;
    while Actual^.Sig <> nil do
      Actual := Actual^.Sig;
    Actual^.Sig := Nuevo;
  end;
  writeln('Estudiante agregado correctamente!');
end;

procedure ListarEstudiantes;
var
  Actual: PEstudiante;
  i: Integer;
begin
  writeln('--- Lista de Estudiantes ---');
  if Lista = nil then
    writeln('No hay estudiantes registrados.')
  else
  begin
    Actual := Lista;
    i := 1;
    while Actual <> nil do
    begin
      writeln('Estudiante #', i);
      writeln('Nombre: ', Actual^.Nombre);
      writeln('Apellido: ', Actual^.Apellido);
      writeln('Edad: ', Actual^.Edad);
      writeln('Direccion: ', Actual^.Direccion);
      writeln('Carrera: ', Actual^.Carrera);
      writeln('Curso: ', Actual^.Curso);
      writeln('Estado: ', Actual^.Estado);
      writeln('---------------------------');
      Actual := Actual^.Sig;
      inc(i);
    end;
  end;
end;

procedure ActualizarEstudiante;
var
  Actual: PEstudiante;
  NombreBuscado: string;
begin
  writeln('--- Actualizar Estudiante ---');
  write('Ingrese el nombre del estudiante a actualizar: ');
  readln(NombreBuscado);

  Actual := Lista;
  while (Actual <> nil) and (Actual^.Nombre <> NombreBuscado) do
    Actual := Actual^.Sig;

  if Actual = nil then
    writeln('Estudiante no encontrado.')
  else
  begin
    writeln('Editando datos de ', Actual^.Nombre);
    write('Nuevo Nombre: '); readln(Actual^.Nombre);
    write('Nuevo Apellido: '); readln(Actual^.Apellido);
    write('Nueva Edad: '); readln(Actual^.Edad);
    write('Nueva Direccion: '); readln(Actual^.Direccion);
    write('Nueva Carrera: '); readln(Actual^.Carrera);
    write('Nuevo Curso: '); readln(Actual^.Curso);
    write('Nuevo Estado: '); readln(Actual^.Estado);
    writeln('Estudiante actualizado correctamente!');
  end;
end;

procedure EliminarEstudiante;
var
  Actual, Anterior: PEstudiante;
  NombreBuscado: string;
begin
  writeln('--- Eliminar Estudiante ---');
  write('Ingrese el nombre del estudiante a eliminar: ');
  readln(NombreBuscado);

  Actual := Lista;
  Anterior := nil;

  while (Actual <> nil) and (Actual^.Nombre <> NombreBuscado) do
  begin
    Anterior := Actual;
    Actual := Actual^.Sig;
  end;

  if Actual = nil then
    writeln('Estudiante no encontrado.')
  else
  begin
    if Anterior = nil then
      Lista := Actual^.Sig
    else
      Anterior^.Sig := Actual^.Sig;

    Dispose(Actual); 
    writeln('Estudiante eliminado correctamente!');
  end;
end;

procedure Menu;
var
  opcion: Integer;
begin
  repeat
    clrscr;
    writeln('====== CRUD Estudiantes ======');
    writeln('1. Agregar Estudiante');
    writeln('2. Listar Estudiantes');
    writeln('3. Actualizar Estudiante');
    writeln('4. Eliminar Estudiante');
    writeln('5. Salir');
    writeln('==============================');
    write('Seleccione una opcion: ');
    readln(opcion);

    case opcion of
      1: AgregarEstudiante;
      2: ListarEstudiantes;
      3: ActualizarEstudiante;
      4: EliminarEstudiante;
    end;

    if opcion <> 5 then
    begin
      writeln;
      writeln('Presione ENTER para continuar...');
      readln;
    end;

  until opcion = 5;
end;

begin
  Menu;
end.
