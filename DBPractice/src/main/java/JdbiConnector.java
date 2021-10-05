import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import java.io.*;
import java.nio.*;
import java.sql.*;
import java.util.*;

public class JdbiConnector {
    private static Jdbi jdbi;

    JdbiConnector () throws IOException {
        jdbi = Jdbi.create("jdbc:postgresql://localhost:5432/postgres", "root", "password");

        createTables();
        //tsvToDB("title.basics.tsv");
        //tsvToDB("title.episode.tsv");
        //tsvToDB("name.basics.tsv");
        tsvToDB("title.crew.tsv");
        checkmovieTypes();

    }

    private void createTables () { /*
        jdbi.useHandle(handle -> {
            handle.execute("create table TITLEBASICS (tconst varchar NOT NULL, titleType varchar, " +
                    "primaryTitle varchar, originalTitle varchar, isAdult integer, " +
                    "startYear int, endYear int, runtimeMinutes int, genres varchar, PRIMARY KEY (tconst))");
        });
        jdbi.useHandle(handle -> {
            handle.execute("create table TITLEEPISODE (tconst varchar NOT NULL, parentTconst varchar NOT NULL, " +
                    "seasonNumber int, episodeNumber int, PRIMARY KEY (tconst), FOREIGN KEY (parentTconst) REFERENCES TITLEBASICS (tconst))");
        });
        jdbi.useHandle(handle -> {
            handle.execute("create table NAMEBASICS (nconst varchar NOT NULL, primaryName varchar,  " +
                    "birthYear int, deathYear int, primaryProfession varchar, knownforTitles varchar, PRIMARY KEY (nconst))");
        });*/
        jdbi.useHandle(handle -> {
            handle.execute("create table TITLECREW (tconst varchar NOT NULL, directors varchar[],  " +
                    "writers varchar[], PRIMARY KEY (tconst), " +
                    "FOREIGN KEY (directors) REFERENCES NAMEBASICS, FOREIGN KEY (writers) REFERENCES NAMEBASICS)");
        });

    }

    private void checkmovieTypes() {
        try (Handle handle = jdbi.open()) {
            Query query = handle.createQuery("select distinct titletype from public.titlebasics ");
            query.mapToMap();
            handle.createQuery("what type of movies have been uploaded").mapTo(String.class);
            //we receive a ResultIterable object.
            List<Map<String, Object>> results = query.mapToMap().list();
            //Optional<Map<String, Object>> first = query.mapToMap().findFirst();
            System.out.println(results);
        }
    }


    public void tsvToDB (String fileName) throws IOException {
        try (Handle handle = jdbi.open()) {
            handle.useTransaction((Handle h) -> {
                List<Integer> intcols = null;
                try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName))) {
                    if (fileName == "title.basics.tsv") {
                        intcols = Arrays.asList(4, 5, 7);
                    } else if (fileName == "title.episode.tsv") {
                        intcols = Arrays.asList(2, 3);
                    } else if (fileName == "name.basics.tsv") {
                        intcols = Arrays.asList(2, 3);
                    } else if (fileName == "title.crew.tsv") {
                        intcols = Arrays.asList();
                    }
                    String line;
                    List<String> items; // list containing each item as a string.
                    String command;
                    boolean firstLine = true;
                    int count = 0;
                    int failed = 0;

                    outer:
                    while ((line = br.readLine()) != null) {
                        count++;
                        if (count%10000==0) { System.out.println(count);}
                        if (failed%10000==0 && failed>0) { System.out.println("failed: "+failed);}
                        if (count>30000) {break outer;}
                        if (firstLine == true) {
                            firstLine = false;
                        } else {
    //_______________________________________________creating command___________________________________________________

                            String temp; // temporary string used for whatever may come up.
                            line = line.replace("'", "").replace("\\N", "null");
                            items = Arrays.asList(line.split("\t"));
                            for (int i = 0; i < items.size(); i++) {
                                // if it is not a numeric column, add string brackets
                                if (!intcols.contains(i)) {
                                    items.set(i, "'" + items.get(i) + "'");
                                }
                                // if it is a string column but contains null, convert 'null' to null
                                if (items.get(i).contains("'null'")) {
                                    items.set(i, "null");
                                }
                                // convert to arrays  // 'cate1,cate2' becomes ARRAY['cate1', 'cate2']
                                if (true /*items.get(i).contains(",")*/) {
                                    temp = items.get(i);
                                    temp = temp.replace(",", "','");
                                    temp = "ARRAY["+temp+"]";
                                    items.set(i, temp);
                                }
                            }
                            String tablename = fileName.replace(".tsv", "").replace(".", "").toUpperCase();
                            command = "insert into " + tablename + " values (" + String.join(", ", items) + ")";



                            String finalCommand = command;
                            try {
                                h.execute(finalCommand);
                            } catch (Exception e) { failed++; e.printStackTrace(); break outer; }
    //___________________________________________________________________________________________________

                        }
                    } System.out.println("failed: "+ failed);
                }
            });

        }


    }



}
