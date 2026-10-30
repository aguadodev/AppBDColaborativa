

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import dao.AlumnoDAO;
import dao.AlumnoDAOImpl;
import dto.AlumnoDTO;
import dto.RegistroConAlumnoDTO;
//import dto.RegistroDTO;
import util.JbdcUtil;



public class AppEvaluavionDiaria {

    public static void main(String[] args) {
        Connection con = JbdcUtil.getConnection();
        JbdcUtil.executeUpdate("BD/datosPruebas.sql", con);
        AlumnoDAO alumnoDAO = new AlumnoDAOImpl(con);

        Scanner sc = new Scanner(System.in);

        System.out.println("----------MENÚ----------");
        System.out.println("1. Listar Alumnado");
        System.out.println("2. Sortear entre todo el alumnado");
        System.out.println("3. Sortear entre alumnado sin registros");
        System.out.println("4. Listar registros");
        System.out.println("5. Insertar registro ejercicio");
        System.out.println("6. Listar alumno/a con sus registros y calificacion media");
        System.out.println("7. Listar alumnado y calificacion media de cada un@");
        int menu = sc.nextInt();

         switch (menu) {
            case 1 -> {
             List<AlumnoDTO> alumnos = alumnoDAO.listarTodos();
             alumnos.forEach(System.out::println);
            }
            case 2 -> {
              //  AlumnoDTO alumnoElegido = sortearAlumno();
             //   System.out.println(alumnoElegido);
            }
            case 3 -> {
            //    AlumnoDTO alumnoSinRegistro = sortearSinRegistros();
              //  System.out.println(alumnoSinRegistro);
            }
            case 4 -> {
              //  List<RegistroConAlumnoDTO> registroConAlumno = listarConAlumno();
             //   System.out.println(registroConAlumno);
            }
            case 5 -> {
             //   if (insertar(RegistroDTO registro)) {
             //       System.out.println("Registro insertado Correctamente");
              //  } else {
              //      System.out.println("Error al insertar el registro");
             //   }
                
            }
            case 6 -> {
               // listarAlumnoConRegistrosYMedia();
            }
            case 7 -> {
               // listarAlumnadoYCalificacionDeCadaUno();
            }

            default -> System.out.println("Opcion no valida");
        }
        
    }
}
