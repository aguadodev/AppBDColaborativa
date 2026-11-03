
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dao.AlumnoDAO;
import dao.AlumnoDAOImpl;
import dao.RegistroDAO;
import dao.RegistroDAOImpl;
import dto.AlumnoDTO;
import dto.RegistroConAlumnoDTO;
import dto.RegistroDTO;
//import dto.RegistroDTO;
import util.JbdcUtil;

public class AppEvaluavionDiaria {

    public static void main(String[] args) {
        Connection con = JbdcUtil.getConnection();
        //JbdcUtil.executeUpdate("BD/datosPruebas.sql", con);
        AlumnoDAO alumnoDAO = new AlumnoDAOImpl(con);
        RegistroDAO registroDAO = new RegistroDAOImpl(con);

        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("----------MENÚ----------");
            System.out.println("1. Listar Alumnado");
            System.out.println("2. Sortear entre todo el alumnado");
            System.out.println("3. Sortear entre alumnado sin registros");
            System.out.println("4. Listar registros");
            System.out.println("5. Insertar registro ejercicio");
            System.out.println("6. Listar alumno/a con sus registros y calificacion media");
            System.out.println("7. Listar alumnado y calificacion media de cada un@");
            System.out.println("8. Salir");
            int menu = sc.nextInt();
            sc.nextLine();
            switch (menu) {
                case 1 -> {
                    List<AlumnoDTO> alumnos = alumnoDAO.listarTodos();
                    alumnos.forEach(System.out::println);
                }
                case 2 -> {
                    AlumnoDTO alumnoElegido = alumnoDAO.sortearAlumno();
                    System.out.println(alumnoElegido);
                }
                case 3 -> {
                    AlumnoDTO alumnoSinRegistro = alumnoDAO.sortearSinRegistros();
                    System.out.println(alumnoSinRegistro);
                }
                case 4 -> {
                    // List<RegistroConAlumnoDTO> registroConAlumno = listarConAlumno();
                    // System.out.println(registroConAlumno);
                }
                case 5 -> {
                    List<AlumnoDTO> alumnos = alumnoDAO.listarTodos();
                    alumnos.forEach(System.out::println);

                    System.out.print("\nSeleccione el ID del alumno que realiza la tarea: ");
                    int id_alumno = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nombre de la tarea: ");
                    String nombre = sc.nextLine();

                    System.out.print("Fecha de realización (yyyy-MM-dd): ");
                    String fechaStr = sc.nextLine();

                    String[] fechaSplit = fechaStr.split("-");

                    LocalDate fecha = LocalDate.of(Integer.valueOf(fechaSplit[0]), Integer.valueOf(fechaSplit[1]), Integer.valueOf(fechaSplit[2]));

                    System.out.print("Puntuación (0 - 5 / vacío si no tiene aún): ");
                    String puntuacionInput = sc.nextLine();

                    Integer puntuacion = null;

                    if (!puntuacionInput.isEmpty()) {
                        puntuacion = Integer.parseInt(puntuacionInput);
                    }

                    if (registroDAO.insertar(new RegistroDTO(nombre, fecha, id_alumno, puntuacion))) {
                        System.out.println("\nRegistro insertado correctamente.");
                    } else {
                        System.out.println("\nError al insertar el registro.");
                    }
                }
                case 6 -> {
                    // listarAlumnoConRegistrosYMedia();
                }
                case 7 -> {
                    // listarAlumnadoYCalificacionDeCadaUno();
                }
                case 8 -> {
                    salir = true;
                }
                default -> System.out.println("Opcion no valida");
            }
        }
        sc.close();
    }
}
