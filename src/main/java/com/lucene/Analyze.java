package com.lucene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class Analyze {
	private static final String INDEX_DIR = "c:/temp/lucene6index";

	private static IndexWriter createWriter() throws IOException {
		FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}

	private static Document createDocument(Integer id, String firstName, String lastName, String website) {
		Document document = new Document();
		document.add(new StringField("id", id.toString(), Field.Store.YES));
		document.add(new TextField("firstName", firstName, Field.Store.YES));
		document.add(new TextField("lastName", lastName, Field.Store.YES));
		document.add(new TextField("website", website, Field.Store.YES));
		return document;
	}

	private static List<Document> createDocument() throws IOException {
		List<Document> documents = new ArrayList<>();
		Scanner sc = new Scanner(new File("C:\\temp\\testout.txt"));
		AtomicInteger a = new AtomicInteger(1);
		while (sc.hasNextLine()) {
			Document document = new Document();
			String[] s = sc.nextLine().split(" ");
			document.add(new StringField("id", Integer.toString(a.getAndIncrement()), Field.Store.YES));
			document.add(new TextField("firstName", s[0], Field.Store.YES));
			document.add(new TextField("lastName", s[1], Field.Store.YES));
			document.add(new TextField("vmmid", s[2], Field.Store.YES));
			documents.add(document);
			if((a.get()%1000)==0) {
				IndexWriter writer=createWriter();
				writer.addDocuments(documents);
				writer.commit();
				writer.close();
				documents=new ArrayList<>();
				System.out.println("Done"+a.get());
			}
		}
		
		IndexWriter writer=createWriter();
		writer.addDocuments(documents);
		writer.commit();
		writer.close();
		documents=new ArrayList<>();
		
		return documents;
	}

	public static void main(String[] args) throws Exception {
		//IndexWriter writer = createWriter();
		// List<Document> documents = new ArrayList<>();
		//
		// Document document1 = createDocument(1, "Lokesh", "Gupta",
		// "howtodoinjava.com");
		// documents.add(document1);
		//
		// Document document2 = createDocument(2, "Brian", "Schultz", "example.com");
		// documents.add(document2);

		// Let's clean everything first
		//writer.deleteAll();

		//writer.addDocuments(createDocument());

		//writer.commit();

		//writer.close();
		createDocument();
	}

}
