class bin{
    /* FILE = MysqlDB.scala, DATE = 4/4/2020 REASON = ['Cumbersome and useless']
        def getDataFrom(columns: String, table: String): stuff.ArrayBuffer[User] ={
        var result: stuff.ArrayBuffer[User] = stuff.ArrayBuffer()
        try {
            // make the connection
            Class.forName(driver)
            connection = DriverManager.getConnection(url)
            // create the statement, and run the select query
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(s"SELECT $columns FROM $table")
            result.clear()
            while ( resultSet.next() ) {
                // val id = resultSet.getString("id")
                val name = resultSet.getString("name")
                val user = resultSet.getString("usr")
                val pass = resultSet.getString("pass")
                val cls = resultSet.getString("cls")
                val sid = resultSet.getString("sid")
                result += new User(name, user, pass, cls, sid)

            }
        } catch {
            case e: com.mysql.cj.jdbc.exceptions.CommunicationsException => println("Server unreachable!")
        }
        println("Got "+result.length+" results for query ['"+table+"']")
        result
    }

    def populate(): Boolean = {
        try {
            // make the connection
            Class.forName(driver)
            connection = DriverManager.getConnection(s"$url?user=root&pass=")
            // create the statement, and run the select query
            val statement = connection.createStatement()
            // val resultSet = statement.executeQuery(s"INSERT INTO `students` (`name`, `usr`, `pass`, `cls`, `sid`) VALUES ('${Tokener.sid}', '${Tokener.sid}', '${Tokener.sid}', '${Tokener.sid}', '$token')")
            val resultSet = statement.execute(s"INSERT INTO `students` (`name`, `usr`, `pass`, `cls`, `sid`) VALUES ('DEMO', 'USER', '${Randomizer.randomPwd}', '${Randomizer.randomClass()}', '${Randomizer.sid}')")
            true
        } catch {
            case e: com.mysql.cj.jdbc.exceptions.CommunicationsException => println("Server unreachable!"); false
        }
    }

    def addUser(user: NewUser): Boolean = {
        try {
            // make the connection
            Class.forName(driver)
            connection = DriverManager.getConnection(s"$url?user=root&pass=")
            // val token = Tokener.gen(10, 20)
            // create the statement, and run the select query
            val statement = connection.createStatement()
            val resultSet = statement.execute(s"INSERT INTO `students` (`name`, `usr`, `pass`, `cls`, `sid`) VALUES ('${user.name}', '${user._user}', '${user._pass}', '${user._cls}', '${user._sid}')")
            return resultSet
        } catch {
            case e: com.mysql.cj.jdbc.exceptions.CommunicationsException => println("Server unreachable!");false
        }
    }

    def select(columns: String, table: String, condition: String): stuff.ArrayBuffer[String] ={
        var result: stuff.ArrayBuffer[String] = stuff.ArrayBuffer()
        // there's probably a better way to do this
        var connection: Connection = null

        try {
            // make the connection
            Class.forName(driver)
            connection = DriverManager.getConnection(url)

            // create the statement, and run the select query
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(s"SELECT $columns FROM $table $condition")
            while ( resultSet.next() ) {
                val sid = resultSet.getString("sid")
                result += sid

            }
        } catch {
            case e: com.mysql.cj.jdbc.exceptions.CommunicationsException => println("Server unreachable!")
        }
        result
    }

    //Auth user functions
    def authStudent(user: String, pass: String): Boolean ={
        var connection: Connection = null;

        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url);

            val statement: java.sql.Statement = connection.createStatement();
            val resultSet: java.sql.ResultSet = statement.executeQuery(StringContext.apply(wrapRefArray(Array.apply("SELECT * FROM students WHERE BINARY user='", "' AND pass='", "'")): _*).s(wrapRefArray(Array.apply(user, pass)): _*));
            resultSet.getBoolean(0);
        } catch {
            case e: com.mysql.cj.exceptions.CJCommunicationsException => println("Server unreachable!");
                false
        };
    }
     */
    /*

     */

}