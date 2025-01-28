
//RTD Es la abreviacion para Real Time Database
package com.mycompany.idmanager;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class IdManager {
    public FirebaseDatabase firebaseDatabase;
    public void initRTD(){ 
        
        //https://pacopruebas-165de-default-rtdb.firebaseio.com/ Link al repositorio de Juanes
        //C:\\Users\\USUARIO\\OneDrive\\Documentos\\NetBeansProjects\\IdManager\\pacopruebas-165de-firebase-adminsdk-fbsvc-6eb7c97568.json Ruta en el PC de juanes, al final ponen el nombre del archivo 
        try {
            // Ruta al archivo JSON de credenciales
            String pathToServiceAccount = "C:\\Users\\USUARIO\\OneDrive\\Documentos\\NetBeansProjects\\IdManager\\pacopruebas-165de-firebase-adminsdk-fbsvc-6eb7c97568.json";

            // Construir opciones de Firebase
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://pacopruebas-165de-default-rtdb.firebaseio.com/")
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(pathToServiceAccount)))
                    .build();
             if (FirebaseApp.getApps().isEmpty()) { // Verificar que no se inicialice más de una vez
                FirebaseApp.initializeApp(firebaseOptions);
            }

            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Conexion exitosa a Firebase RTD.");
        }catch (RuntimeException ex) {
            System.out.println("Problema ejecucion ");
        }catch (FileNotFoundException ex) {
            System.out.println("Problema archivo");
        }catch(IOException e){
            System.out.println("Error al cargar las credenciales: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        IdManager manager = new IdManager();
        manager.initRTD();
        System.out.println("==== Bienvenido a ID Manager =====");
        Scanner s = new Scanner(System.in);
        int opcion1 = -1;
        int opcion2 = -1;
        int opcion3 = -1;
        String nodo;
        String clave;
        String nombre;
        int edad;
        String correo;
        String direccion;
        
        
        do{
            try{
                System.out.println("=== Menu Principal ===");
                System.out.println("1. Gestionar Empleados");
                System.out.println("2. Extractor de C.C");
                System.out.println("3. Salir");
                System.out.print("Elige una opcion: ");
                System.out.println();
                opcion1 = s.nextInt();                
            }catch(InputMismatchException e){
                System.out.println("Error el valor ingresado no es valido, reintentar");
                System.out.println();
                break;
            }
            switch(opcion1){
                case 1:
                    try{
                        System.out.println("=== Menu Principal ===");
                        System.out.println("1. Agregar empleado");
                        System.out.println("2. Ver info empleados");
                        System.out.println("3. Actualizar info empleado");
                        System.out.println("4. Eliminar empleados");
                        System.out.println("5. Salir");
                        System.out.print("Elige una opcion: ");
                        opcion2 = s.nextInt();                
                    }catch(InputMismatchException e){
                        System.out.println("Error el valor ingresado no es valido, reintentar");
                        System.out.println();
                        break;
                    }
                    switch(opcion2){
                        case 1:
                            
                            s.nextLine();//Limpia buffer para que lea bien
                            System.out.println("Empresa a agregar: ");
                            nodo = s.nextLine();
                            System.out.println("ID empleado: ");
                            clave = s.nextLine();
                            System.out.println("Datos empleado: ");
                            System.out.println("Nombre: ");
                            nombre = s.nextLine();
                            System.out.println("Edad: ");
                            edad = s.nextInt();
                            s.nextLine();//Limpia buffer para que lea bien
                            System.out.println("Correo: ");
                            correo = s.nextLine();
                            System.out.println("Direccion: ");
                            direccion = s.nextLine();
                            
                            //Creamos el objeto tipo persona a añadir
                            
                            Persona persona = new Persona(nombre,edad,correo,direccion);
                            AsistenteFirebase.agregarInfo(manager.firebaseDatabase,nodo,clave,persona);
                            break;
                        case 2:
                            s.nextLine();//Limpia buffer para que lea bien
                            System.out.println("Ingrese la empresa a la que pertence el empleado: ");
                            nodo = s.nextLine();
                            System.out.println("Ingrese el ID del empleado a consultar: ");
                            clave = s.nextLine();
                            AsistenteFirebase.leerInfo(manager.firebaseDatabase,nodo,clave);
                            
                            break;
                        case 3:
                            try{
                                s.nextLine();//Limpia buffer para que lea bien
                                System.out.println("Ingrese la empresa a la que pertence el empleado: ");
                                nodo = s.nextLine();
                                System.out.println("Ingrese el ID del empleado a actualizar: ");
                                clave = s.nextLine();
                                Persona persona2 = AsistenteFirebase.obtenerInfo(manager.firebaseDatabase, nodo, clave);
                                String nombre2 = persona2.getNombre();
                                int edad2 = persona2.getEdad();
                                String correo2 = persona2.getCorreo();
                                String direccion2 = persona2.getDireccion();
                               
                            
                                try{
                                    System.out.println("Que info desea actualizar?");
                                    System.out.println("1. Nombre");
                                    System.out.println("2. Edad");
                                    System.out.println("3. Correo");
                                    System.out.println("4. Direccion");
                                    System.out.print("Elige una opcion: ");
                                    opcion3 = s.nextInt();
                                
                                }catch(InputMismatchException e){
                                    System.out.println("Error el valor ingresado no es valido, reintentar");
                                    System.out.println();
                                break;
                                }switch(opcion3){
                                    case 1:
                                        s.nextLine();
                                        System.out.println("Ingrese actualizacion de nombre: ");
                                        String nuevoNombre = s.nextLine();
                                        Persona nuevoNombrePersona = new Persona(nuevoNombre,edad2,correo2,direccion2);
                                        AsistenteFirebase.actualizarInfo(manager.firebaseDatabase,nodo,clave,nuevoNombrePersona);
                                        break;
                                    case 2:
                                        s.nextLine();
                                        System.out.println("Ingrese actualizacion de edad: ");
                                        int nuevaEdad = s.nextInt();
                                        Persona nuevaEdadPersona = new Persona(nombre2,nuevaEdad,correo2,direccion2);
                                        AsistenteFirebase.actualizarInfo(manager.firebaseDatabase,nodo,clave,nuevaEdadPersona);
                                        break;
                                    case 3:
                                        s.nextLine();
                                        System.out.println("Ingrese actualizacion de correo: ");
                                        String nuevoCorreo = s.nextLine();
                                        Persona nuevoCorreoPersona = new Persona(nombre2,edad2,nuevoCorreo,direccion2);
                                        AsistenteFirebase.actualizarInfo(manager.firebaseDatabase,nodo,clave,nuevoCorreoPersona);
                                        break;
                                    case 4:
                                        s.nextLine();
                                        System.out.println("Ingrese actualizacion de direccion: ");
                                        String nuevaDireccion = s.nextLine();
                                        Persona nuevaDireccionPersona = new Persona(nombre2,edad2,correo2,nuevaDireccion);
                                        AsistenteFirebase.actualizarInfo(manager.firebaseDatabase,nodo,clave,nuevaDireccionPersona);
                                        break;     
                                }
                            }catch(InterruptedException ex){
                                System.out.println("Error al obtener informacion: " + ex.getMessage());
                                break;
                            }
                            
                            break;
                        case 4:
                            AsistenteFirebase.borrarInfo();
                            break;
                        case 5:
                            System.out.println("Saliendo...");
                            break;
                        default:
                            
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Esta funcion aun no está completa");
                    break;
                case 3:
                    System.out.println("Saliendo...");
                     break;

                default:
                    
            }        
        }while(opcion1 != 3);   
        
    }
}
