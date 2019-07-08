package co.edu.udistrital.user.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.edu.udistrital.configuration.CommunicationApplication;
import co.edu.udistrital.structure.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CommunicationApplication.class})
public class UserServiceTest {

	@Autowired private UserService userService;

	@Test
	public void save() {
		User user = new User();
		user.setEmail("emartinez@gmail.com");
		user.setMobilePhone("3205541126");
		userService.userSingleRegister(user, null);
		System.out.println("Finish save Operation");
	}

	List<User> userList;

	@Before
	public void loadList() {
		userList = new ArrayList<>(1);
		userList.add(new User("Rubiel Alberto Agudelo Echeverry", "umata@yopmail.com", "3115526632"));
		userList.add(new User("Jhon Alexander Alvarez Puerta", "desarrollocomunitario@yopmail.com", "3115526633"));
		userList.add(new User("Alejandra Maria Arango Alvarez", "dls@yopmail.com", "3115526634"));
		userList.add(new User("Jaider Egidio Arango Arango", "angosturaestereo89.2@yopmail.com", "3115526635"));
		userList.add(new User("Marlen Haygnes Arango Posada", "comisaria@yopmail.com", "3115526636"));
		userList.add(new User("Johana Alexandra Atehortua Agudelo", "personería@yopmail.com", "3115526637"));
		userList.add(new User("Nai de Jesus Atehortua Atehortua", "gobierno@yopmail.com", "3115526638"));
		userList.add(new User("Luz Elena Ayala Mira", "casamuseo.angostura@yopmail.com", "3115526639"));
		userList.add(new User("Jhon  Jairo Barrientos González", "gobierno@yopmail.com", "3115526640"));
		userList.add(new User("Mauro Albeiro Betancur  Pineda", "planeacion@yopmail.com", "3115526641"));
		userList.add(new User("Luis  Reinaldo Bustamante Taborda", "tesoreria@yopmail.com", "3115526642"));
		userList.add(new User("Yulieth  Cardenas Chaverra", "umata@yopmail.com", "3115526643"));
		userList.add(new User("Daniel  Cardona Loaiza", "gobierno@yopmail.com", "3115526644"));
		userList.add(new User("Jeisson Alejandro Castaño Piedrahita", "ambiente@yopmail.com", "3115526645"));
		userList.add(new User("Carlos Ignacio Castaño Piedrahita", "dls@yopmail.com", "3115526646"));
		userList.add(new User("Hernado de Jesús Echeverry Zapata", "tesoreria@yopmail.com", "3115526647"));
		userList.add(new User("Jose Alirio Fernandez Gómez", "gobierno@yopmail.com", "3115526648"));
		userList.add(new User("María Maryory García  Moreno", "tesoreria@yopmail.com", "3115526649"));
		userList.add(new User("Jorge  Ignacio Gaviria Rua", "gobierno@yopmail.com", "3115526650"));
		userList.add(new User("Lina Marcela Giraldo Pérez", "gestiondocumental@yopmail.com", "3115526651"));
		userList.add(new User("Liliana Maria Gomez Barrientos", "concejo@yopmail.com", "3115526652"));
		userList.add(new User("Miller Jaime Gomez Blandon", "mujer@yopmail.com", "3115526653"));
		userList.add(new User("Andres Felipe Gómez Zapata", "gobierno@yopmail.com", "3115526654"));
		userList.add(new User("Isbelia Maria Gonzalez Taborda", "educacion@yopmail.com", "3115526655"));
		userList.add(new User("Lina Marcela Grisales  Ortiz", "tesoreria@yopmail.com", "3115526656"));
		userList.add(new User("Johana Maria Guzman Zapata", "tesoreria@yopmail.com", "3115526657"));
		userList.add(new User("Martha  Elena Henao Zapata", "gobierno@yopmail.com", "3115526658"));
		userList.add(new User("Marisol  Hernandez Villa", "planeacion@yopmail.com", "3115526659"));
		userList.add(new User("Cristian Dario Hincapie Gonzalez", "inspecion@yopmail.com", "3115526660"));
		userList.add(new User("Juan  Guillermo Londoño Vasquez", "planeacion@yopmail.com", "3115526661"));
		userList.add(new User("Ruth Teresita Lopera  Rojas", "gobierno@yopmail.com", "3115526662"));
		userList.add(new User("Solina Maria Lopera  Zapata", "gobierno@yopmail.com", "3115526663"));
		userList.add(new User("Lizeth Juliana Lopez Mira", "gobierno@yopmail.com", "3115526664"));
		userList.add(new User("Sandra Mariana Lujan  Sanchez", "juventud@yopmail.com", "3115526665"));
		userList.add(new User("Esmeralda Lucia Madrigal  Hoyos ", "madrigal2266@yopmail.com", "3115526666"));
		userList.add(new User("Carmen  Elena Martinez Velez", "casamuseo.angostura@yopmail.com", "3115526667"));
		userList.add(new User("Monica Patricia Mendoza Montoya", "gobierno@yopmail.com", "3115526668"));
		userList.add(new User("Luz Marina Montoya Villegas", "desarrollocomunitario@yopmail.com", "3115526669"));
		userList.add(new User("Rubiel Alberto Mora  Gonzalez", "umata@yopmail.com", "3115526670"));
		userList.add(new User("Yohan Adrian Mora  Taborda", "casamuseo.angostura@yopmail.com", "3115526671"));
		userList.add(new User("Jhon  William Munera Parra", "desarrollocomunitario@yopmail.com", "3115526672"));
		userList.add(new User("Wilsón  Germán Ospina Escobar", "umata@yopmail.com", "3115526673"));
		userList.add(new User("Fernando de Jesus Pareja Arango", "angosturaestereo89.2@yopmail.com", "3115526674"));
		userList.add(new User("Diego Alejandro Pemberthy Lopera", "personeria@yopmail.com", "3115526675"));
		userList.add(new User("Yasmir Andrea Perez Gaviria", "planeacion@yopmail.com", "3115526676"));
		userList.add(new User("Genni Catalina Piedrahita Giraldo", "depote@yopmail.com", "3115526677"));
		userList.add(new User("Yomaira Andrea Porras  Agudelo", "gobierno@yopmail.com", "3115526678"));
		userList.add(new User("Marta  Lucia Porras  Arenas", "alcaldia@yopmail.com", "3115526679"));
		userList.add(new User("Homero  Esteban Posada  Hoyos ", "gobierno@yopmail.com", "3115526680"));
		userList.add(new User("Orlando Antonio Posada  Valencia", "planeacion@yopmail.com", "3115526681"));
		userList.add(new User("Jhon Fredy Restrepo  Cárdenas", "depote@yopmail.com", "3115526682"));
		userList.add(new User("Olga Ines Restrepo  Prisco", "gobierno@yopmail.com", "3115526683"));
		userList.add(new User("Hernan Dario Restrepo  Prisco", "planeacion@yopmail.com", "3115526684"));
		userList.add(new User("Gloria Eugenia Rios Monsalve", "sisben@yopmail.com", "3115526685"));
		userList.add(new User("Ana Maria Rojas Martinez", "discapacidad@yopmail.com", "3115526686"));
		userList.add(new User("Diana Marcela Rojo Atehortua", "justicia@yopmail.com", "3115526687"));
		userList.add(new User("Darwin Arley Rojo Macias", "gobierno@yopmail.com", "3115526688"));
		userList.add(new User("Ana Yenifer Rua Valencia", "gobierno@yopmail.com", "3115526689"));
		userList.add(new User("Marleny  Sanchez Piedrahita", "tesoreria@yopmail.com", "3115526690"));
		userList.add(new User("Humberto  León Sanchez  Gomez", "tesoreria@yopmail.com", "3115526691"));
		userList.add(new User("Juan  José Sepúlveda Orrego", "umata@yopmail.com", "3115526692"));
		userList.add(new User("luz  Amparo Sierra Betancur", "dls@yopmail.com", "3115526693"));
		userList.add(new User("Juan  Carlos Soto Lennis", "gobierno@yopmail.com", "3115526694"));
		userList.add(new User("Margarita Maria Soto Misas", "gerontologia@yopmail.com", "3115526695"));
		userList.add(new User("Ruth Marleny Soto Zapata", "almacen@yopmail.com", "3115526696"));
		userList.add(new User("Juan  Alberto Soto  Angulo", "depote@yopmail.com", "3115526697"));
		userList.add(new User("Maria de las Mercedes Soto  Angulo", "planeacion@yopmail.com", "3115526698"));
		userList.add(new User("Eliana  Patricia Tobón  Cardona", "elianatobonc@yahoo.es ", "3115526699"));
		userList.add(new User("Luis  Fernando Vargas Correa", "planeacion@yopmail.com", "3115526700"));
		userList.add(new User("Joaquin Emilio Vélez Argáez", "gobierno@yopmail.com", "3115526701"));
		userList.add(new User("Jhuliana  Maria Velez Loaiza", "umata@yopmail.com", "3115526702"));
		userList.add(new User("Luz Mariana Velez Zea", "comisaria@yopmail.com", "3115526703"));
		userList.add(new User("Juan  Alberto Villa Amaya", "planeacion@yopmail.com", "3115526704"));
		userList.add(new User("Jose  Fernando Villa Mesa", "umata@yopmail.com", "3115526705"));
		userList.add(new User("Andres Felipe Villa Taborda", "depote@yopmail.com", "3115526706"));
		userList.add(new User("John  Jairo Zea Henao", "gerontologia@yopmail.com", "3115526707"));
		userList.add(new User("Javier Ignacio Molina Cano ", "javier@yopmail.com", "3115526708"));
		userList.add(new User("Lillian Eugenia Gómez Álvarez", "lillian@yopmail.com", "3115526709"));
		userList.add(new User("Sixto Naranjo Marín", "sixto@yopmail.com", "3115526710"));
		userList.add(new User("Gerardo Emilio Duque Gutiérrez", "gerardo@yopmail.com", "3115526711"));
		userList.add(new User("Jhony Alberto Sáenz Hurtado ", "jhony@yopmail.com", "3115526712"));
		userList.add(new User("Germán Antonio Lotero Upegui", "germán@yopmail.com", "3115526713"));
		userList.add(new User("Oscar Darío Murillo González", "oscar@yopmail.com", "3115526714"));
		userList.add(new User("Augusto Osorno Gil ", "augusto@yopmail.com", "3115526715"));
		userList.add(new User("César Oswaldo Palacio Martínez  ", "césar@yopmail.com", "3115526716"));
		userList.add(new User("Gloria Amparo Alzate Agudelo  ", "gloria@yopmail.com", "3115526717"));
		userList.add(new User("Héctor Iván González Castaño ", "héctor@yopmail.com", "3115526718"));
		userList.add(new User("Beatriz Elena Osorio Laverde ", "beatriz@yopmail.com", "3115526719"));
		userList.add(new User("Herman Correa Ramírez ", "herman@yopmail.com", "3115526720"));
		userList.add(new User("Carlos Mario Montoya Serna ", "carlos@yopmail.com", "3115526721"));
		userList.add(new User("Carlos Augusto Giraldo ", "carlos@yopmail.com", "3115526722"));
		userList.add(new User("Arturo Tabares Mora ", "arturo@yopmail.com", "3115526723"));
		userList.add(new User("William de J Ramírez Vásquez ", "william@yopmail.com", "3115526724"));
		userList.add(new User("Jaime Lopez Tobón ", "jaime@yopmail.com", "3115526725"));
		userList.add(new User("Gloria Elena Sanclemente Zea ", "gloria@yopmail.com", "3115526726"));
		userList.add(new User("Carlos Alberto Villegas Lopera ", "carlos@yopmail.com", "3115526727"));
		userList.add(new User("Jorge Uribe Botero ", "jorge@yopmail.com", "3115526728"));
		userList.add(new User("Maria Isabel López Gaviria ", "maria@yopmail.com", "3115526729"));
		userList.add(new User("Alfredo Tobón Tobón ", "alfredo@yopmail.com", "3115526730"));
		userList.add(new User("Héctor Damián Mosquera Benítez ", "héctor@yopmail.com", "3115526731"));
		userList.add(new User("Álvaro Iván Berdugo López ", "álvaro@yopmail.com", "3115526732"));
		userList.add(new User("Carlos Alberto Zárate Yépez ", "carlos@yopmail.com", "3115526733"));
		userList.add(new User("Hernán Darío Hurtado Pérez ", "hernán@yopmail.com", "3115526734"));
		userList.add(new User("Jorge León Ruiz Ruiz ", "jorge@yopmail.com", "3115526735"));
		userList.add(new User("John Jairo Duque García ", "john@yopmail.com", "3115526736"));
		userList.add(new User("Armid Benjamín Muñoz Ramírez ", "armid@yopmail.com", "3115526737"));
		userList.add(new User("Elkin Octavio Díaz Pérez ", "elkin@yopmail.com", "3115526738"));
		userList.add(new User("Julio Cesar Rodas Monsalve ", "julio@yopmail.com", "3115526739"));
		userList.add(new User("Gabriel Jaime Jiménez Gómez ", "gabriel@yopmail.com", "3115526740"));
		userList.add(new User("José Didier Zapata Suárez  ", "josé@yopmail.com", "3115526741"));
		userList.add(new User("Bernardo Posada Vera ", "bernardo@yopmail.com", "3115526742"));
		userList.add(new User("Luis Guillermo Vélez Osorio ", "luis@yopmail.com", "3115526743"));
		userList.add(new User("Horacio Augusto Moreno Correa ", "horacio@yopmail.com", "3115526744"));
		userList.add(new User("Alejandro Alzate Garcés ", "alejandro@yopmail.com", "3115526745"));
		userList.add(new User("Javier Ignacio Aguilar Gómez ", "javier@yopmail.com", "3115526746"));
		userList.add(new User("Adolfo León Correa Silva ", "adolfo@yopmail.com", "3115526747"));
		userList.add(new User("Gustavo Hernán Rodríguez Vallejo ", "gustavo@yopmail.com", "3115526748"));
		userList.add(new User("Oscar Darío Gómez Giraldo ", "oscar@yopmail.com", "3115526749"));
		userList.add(new User("Gonzalo López Gaviria ", "gonzalo@yopmail.com", "3115526750"));
		userList.add(new User("Héctor Manuel Pineda  ", "héctor@yopmail.com", "3115526751"));
		userList.add(new User("Maria Victoria Arias Gómez ", "maria@yopmail.com", "3115526752"));
		userList.add(new User("Luis Alfonso Escobar Trujillo ", "luis@yopmail.com", "3115526753"));
		userList.add(new User("Lida Patricia Giraldo Morales ", "lida@yopmail.com", "3115526754"));
		userList.add(new User("Luis Oliverio Cárdenas Moreno ", "luis@yopmail.com", "3115526755"));
		userList.add(new User("Luis Fernando Castro Hernández", "luis@yopmail.com", "3115526756"));
		userList.add(new User("Julio Cesar Rodríguez Monsalve", "julio@yopmail.com", "3115526757"));
		userList.add(new User("Álvaro de Jesús Saldarriaga Vásquez", "álvaro@yopmail.com", "3115526758"));
		userList.add(new User("Luis Aníbal Sepúlveda Villada ", "luis@yopmail.com", "3115526759"));
		userList.add(new User("Beatriz Elena Puerta Bolívar", "beatriz@yopmail.com", "3115526760"));
		userList.add(new User("Ángel Gabriel Arrubla Ortiz", "ángel@yopmail.com", "3115526761"));
		userList.add(new User("Álvaro de Jesús Bocanumenth Puerta", "álvaro@yopmail.com", "3115526762"));
		userList.add(new User("Fabio Alexander Florez García", "fabio@yopmail.com", "3115526763"));
		userList.add(new User("Héctor Darío Bermúdez Saldarriaga", "héctor@yopmail.com", "3115526764"));
	}


	private List<User> getListToProcess(int records) {
		List<User> userToProcess = new ArrayList<>(records);
		for (int i = 0; i < records; i++) {
			userToProcess.add(userList.get(getRandom()));
		}
		return userToProcess;
	}

	@Test
	public void saveOne() {
		processUser(1);
	}


	private void processUser(int userNumber) {
		long startTime = System.currentTimeMillis();
		List<User> userToProcess = getListToProcess(userNumber);
		System.out.println(MessageFormat.format("----------------VAMOS A PROCESAR {0} USUARIOS------------------", userNumber));
		for (User u : userToProcess) {
			userService.userSingleRegister(u, null);
		}
		System.out.println("Finish save Operation");
		long endTime = System.currentTimeMillis();
		System.out.println("La operación se realizó en " + (endTime - startTime) + " milisegundos.");
	}

	private int getRandom() {
		return Double.valueOf(Math.floor(Math.random() * (130 - 1 + 1) + 1)).intValue();
	}

	@Test
	public void saveRange() throws InterruptedException {
		int userNumber = 0;
		for (int i = 0; i < 100; i++) {
			userNumber = getRandom();
			processUser(userNumber);
			Thread.sleep((long) 2000);
		}
	}



}
