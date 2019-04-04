package com.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneReadIndexExample {
	private static final String INDEX_DIR = "c:/temp/lucene6index";

	public static void main(String[] args) throws Exception {
		IndexSearcher searcher = createSearcher();

		// Search by ID
		// TopDocs foundDocs = searchById(1, searcher);
		//
		// System.out.println("Total Results :: " + foundDocs.totalHits);
		//
		// for (ScoreDoc sd : foundDocs.scoreDocs) {
		// Document d = searcher.doc(sd.doc);
		// System.out.println(String.format(d.get("firstName")));
		// }

		// Search by firstName
		// TopDocs foundDocs2 = searchByFirstName("Brian", searcher);
		//
		// System.out.println("Total Results :: " + foundDocs2.totalHits);
		//
		// for (ScoreDoc sd : foundDocs2.scoreDocs) {
		// Document d = searcher.doc(sd.doc);
		// System.out.println(String.format(d.get("id")));
		// }

		TopDocs foundDocs2 = partialSearch("*", searcher);
		System.out.println("Total Results :: " + foundDocs2.totalHits);

		for (ScoreDoc sd : foundDocs2.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			System.out.println(d.get("id")+" "+d.get("firstName")+" "+d.get("lastName")+" "+d.get("vmmid"));
			//System.out.println(String.format(d.get("firstName")));
			//System.out.println(String.format(d.get("lastName")));
		}

	}

	private static TopDocs partialSearch(String firstName, IndexSearcher searcher) throws IOException {
		//QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
		//qp.setAllowLeadingWildcard(true);
		System.out.println(firstName);
		Term term = new Term("firstName", firstName);
		Query query = new WildcardQuery(term);
		TopDocs hits = searcher.search(query,100,Sort.INDEXORDER);
		System.out.println("T"+hits.totalHits);
		return hits;
	}

	private static TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception {
		QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
		Query firstNameQuery = qp.parse(firstName);
		TopDocs hits = searcher.search(firstNameQuery, 10);
		return hits;
	}

	private static TopDocs searchById(Integer id, IndexSearcher searcher) throws Exception {
		QueryParser qp = new QueryParser("id", new StandardAnalyzer());
		Query idQuery = qp.parse(id.toString());
		TopDocs hits = searcher.search(idQuery, 10);
		return hits;
	}

	private static IndexSearcher createSearcher() throws IOException {
		Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		return searcher;
	}
}