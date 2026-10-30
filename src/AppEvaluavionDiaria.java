

import java.util.List;
import java.util.Scanner;

import dto.AlumnoDTO;
import dto.RegistroConAlumnoDTO;
import dto.RegistroDTO;



public class AppEvaluavionDiaria {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("----------MENÚ----------");
        System.out.println("1. Listar Alumnos");
        System.out.println("2. Sortear entre todos los alumnos");
        System.out.println("3. Sortear alumnos sin registro de ejercicios");
        System.out.println("4. Listar registros ejercicios");
        System.out.println("5. Insertar registro ejercicio");
        System.out.println("6. Listar alumno con sus registros y calificacion media");
        System.out.println("7. Listar alumnado y calificacion media de cada un@");
        int menu = sc.nextInt();

         switch (menu) {
            case 1 -> {
                List<AlumnoDTO> alumnos = listarTodos();
                System.out.println(alumnos);
            }
            case 2 -> {
                AlumnoDTO alumnoElegido = sortearAlumno();
                System.out.println(alumnoElegido);
            }
            case 3 -> {
                AlumnoDTO alumnoSinRegistro = sortearSinRegistros();
                System.out.println(alumnoSinRegistro);
            }
            case 4 -> {
                List<RegistroConAlumnoDTO> registroConAlumno = listarConAlumno();
                System.out.println(registroConAlumno);
            }
            case 5 -> {
                if (insertar(RegistroDTO registro)) {
                    System.out.println("Registro insertado Correctamente");
                } else {
                    System.out.println("Error al insertar el registro");
                }
                
            }
            case 6 -> {
                listarAlumnoConRegistrosYMedia();
            }
            case 7 -> {
                listarAlumnadoYCalificacionDeCadaUno();
            }

            default -> System.out.println("Opcion no valida");
        }
        
    }
}
