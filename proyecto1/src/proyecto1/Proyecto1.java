package proyecto1;

public class Proyecto1 {
    
    public static void main(String[] args) {
        
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new editor().setVisible(true);
            }
        });
    }
}