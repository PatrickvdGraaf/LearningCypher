package Chapters;

/**
 * This class is used to summarise tha main points of interest in Chapter 2 of Learning Cypher
 * Created by Patrick van de Graaf
 */
public class Chapter2 extends Chapter {
    //In this chapter, a book-store is used as an example
    //To make the queries work, u would have to download the files from http://www.packtpub.com/support,
    //containing a database with 150 books. But you'll need to purchase the book Learning Cypher itself to get access to it.
    //Since u and I are both students, and probably didn't buy it, this chapter will be pure theoratical and there is no use
    //in running it
    public Chapter2() {
        super();

        /**
         * Text Search
         */
        //This operation is used when a user knows the title exactly
        operation = "MATCH (b:Book { title: 'In Search of Lost Time' })" +
                    "RETURN b";
        //It can also be written this way
        operation = "MATCH (b:Book)" +
                    "WHERE b.title = 'In Search of Lost Time'" +
                    "RETURN b";
        //We would get this result in both cases, and both queries have the same performance result
        //+----------------------------------------------------------+
        //| b                                                        |
        //+----------------------------------------------------------+
        //| Node[143]{title:"In Search of Lost Time",tags:["novel"]} |
        //+----------------------------------------------------------+

        /**
         * Regular expressions
         */
        //Regular expressions are widely used patterns that match expressions for strings. The following
        //query will match all titles containing the word 'Lost'
        //the special character . matches any character, while * matches any occurrence of the previous pattern,
        //and once combined, .* matches any occurrence of any character.
        operation = "MATCH (b:Book)" +
                    "WHERE b.title =~ '.*Lost.*'" +
                    "RETURN b";
        //+----------------------------------------------------------------+
        //| b                                                              |
        //+----------------------------------------------------------------+
        //| Node[143]{title:"In Search of Lost Time",tags:["novel"]}       |
        //| Node[221]{title:"Love's Labour's Lost",tags:["comedy","drama"]}|
        //+----------------------------------------------------------------+

        /**
         * Here is a list of the most widely used patterns
         */
//        Pattern     Meaning                           Example         Matched Strings
//        [ ]         This stands for any character     Te[sx]t         Text Test
//                    included in the bracket or in     [A-C]lt         Alt Blt Clt
//                    the range specified
//
//        *           This implies any occurrence       Te[xs]*t        Text Test Texxt Tet
//                    of the preceding pattern,
//                    including no occurrence
//
//        .           This implies any character,       Te.t            Text Test Tent Tet
//                    including none
//
//        ?           This implies one or no            Te[xs]?t        Text Test Tet
//                    occurrence of the preceding
//                    pattern
//
//        |           This stands for alternative       Te(xt|ll)       Text Tell
//                    matching
//
//        \           This pattern escapes the          Te\.t           Te.t
//                    following reserved character

        /**
         * The following is a list of some of the most widely used regular expression patterns:
         */
//        Regular expression                                Matches
//        ([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})     E-mail

//        (https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})       An HTTP URL without
//        ([\/\w \.-]*)*\/?                                 a query string

//        <([a-z]+)([^<]+)*(?:>(.*)<\/\1>|\s+\/>)           HTML tags

//        \d{5}                                             A ZIP code

//        \+?[\d\s]{3,}                                     A phone number

        //Knowing this, we can make query like this one; it returns all books with a title that starts with Henry
        operation = "MATCH (b:Book)" +
                    "WHERE b.title =~ 'Henry.*'" +
                    "RETURN b";
        //Or all boks that have tale, tales, Tale and Tales in their title
        operation = "MATCH (b:Book)" +
                    "WHERE b.title =~ '.*[Tt]ale(s)?.*'" +
                    "RETURN b";
        //Regular expressions are case sensitive. If you want to match a string independently by a letter's case, use the
        //(?i) specifier at the beginning of the string. This operation will have the same result as the previous one
        operation = "MATCH (b:Book)" +
                    "WHERE b.title =~ '(?i).*tale(s)?.*'" +
                    "RETURN b";
    }
}
