package net.codejava.hibernate;

import java.util.ArrayList;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BookManager {
	protected SessionFactory sessionFactory;
	Scanner sc = new Scanner(System.in);

	protected void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
																									// from
																									// hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			StandardServiceRegistryBuilder.destroy(registry);
		}

	}

	protected void exit() {
		// code to close Hibernate Session factory
		sessionFactory.close();
	}

	protected void create() {
		System.out.println("Titulo: ");
		String titulo = sc.nextLine();
		System.out.println("Autor: ");
		String autor = sc.nextLine();
		System.out.println("Precio: ");
		String price = sc.nextLine();
		Book book = new Book();
		book.setTitle(titulo);
		book.setAuthor(autor);
		book.setPrice(Float.parseFloat(price));

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(book);

		session.getTransaction().commit();
		session.close();
	}

	protected void read() {
		Session session = sessionFactory.openSession();

		System.out.println("Id: ");
		String bookId = sc.nextLine();
		try {
			Book book = session.get(Book.class, Long.parseLong(bookId));
			System.out.println("Title: " + book.getTitle());
			System.out.println("Author: " + book.getAuthor());
			System.out.println("Price: " + book.getPrice());
		} catch (Exception e) {
			// TODO: handle exception
		}

		session.close();
	}

	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		BookManager manager = new BookManager();
		manager.setup();

		int opcion = 0;
		while (opcion != 3) {
			System.out.println("1 - Insertar\n2 - Mostrar Libro\n3 - Salir");
			opcion = Integer.parseInt(sca.nextLine());
			switch (opcion) {
			case 1:
				manager.create();
				break;
			case 2:
				manager.read();
				break;

			default:
				break;
			}
		}

		manager.exit();
	}
}
