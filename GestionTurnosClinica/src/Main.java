import services.Clinica;
import utils.MenuClinica;

public class Main {
    public static void main(String[] args) {
        Clinica clinica = new Clinica();
        MenuClinica menu = new MenuClinica(clinica);
        menu.iniciar();
    }
}
