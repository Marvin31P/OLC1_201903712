package proyecto1;

public class main {
    
    
    
    
    public static void main(String[] args) {
        
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new editor().setVisible(true);
            }
        });
    }
}