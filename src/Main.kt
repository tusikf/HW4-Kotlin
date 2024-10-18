//exit
//help
//add <Имя> phone <Номер телефона>
//add <Имя> email <Адрес электронной почты>

//— Создайте иерархию sealed классов, которые представляют собой команды. В корне иерархии интерфейс Command.
//— В каждом классе иерархии должна быть функция isValid(): Boolean, которая возвращает true,
// если команда введена с корректными аргументами. Проверку телефона и email нужно перенести в эту функцию.
//— Напишите функцию readCommand(): Command, которая читает команду из текстового ввода, распознаёт её и возвращает
// один из классов-наследников Command, соответствующий введённой команде.
//— Создайте data класс Person, который представляет собой запись о человеке. Этот класс должен содержать поля:
//name – имя человека
//phone – номер телефона
//email – адрес электронной почты
//— Добавьте новую команду show, которая выводит последнее значение, введённой с помощью команды add.
// Для этого значение должно быть сохранено в переменную типа Person. Если на момент выполнения команды show не было ничего введено,
// нужно вывести на экран сообщение “Not initialized”.
//— Функция main должна выглядеть следующем образом. Для каждой команды от пользователя:
//Читаем команду с помощью функции readCommand
//Выводим на экран получившийся экземпляр Command
//Если isValid для команды возвращает false, выводим help. Если true, обрабатываем команду внутри when.
sealed interface Command {
    fun isValid(str:String): Boolean

}
class Content(val title: String): Command {
    override fun isValid(str:String): Boolean {
        return false
    }
    object Help: Command {
        override fun isValid(str:String): Boolean {
            if (str == "help") {
                return true
            } else return false
        }
    }
    object Exit: Command {
        override fun isValid(str:String): Boolean {
            if (str == "exit") {
                return true
            } else return false
        }
    }
    object Add: Command {
        //Проверку телефона и email нужно перенести в эту функцию.
        override fun isValid(str:String): Boolean {
            val myArray: List<String> = str!!.split(" ")
            val plus:String = "+"
            if (myArray.size >= 4) {
                when {
                    myArray[2] == "phone" -> {
                        if (myArray[3].matches(Regex("""[0-9]+""")) || myArray[3].startsWith(plus)) {
                            return true
                        } else {
                            return false
                        }
                    }

                    myArray[2] == "email" -> {
                        val slovo1: List<String> = myArray[3].split("@")
                        if (slovo1.size == 2) {
                            val slovo2: List<String> = slovo1[1].split(".")
                            if (slovo1.size == 2 && slovo2.size == 2) {
                                return true
                            } else {
                                return false
                            }
                        } else {
                            return false
                        }

                    }
                }
            } else {
                return false
            }
            return false
        }
    }

    //— Добавьте новую команду show, которая выводит последнее значение, введённой с помощью команды add.
    // Для этого значение должно быть сохранено в переменную типа Person. Если на момент выполнения команды show не было ничего введено,
    // нужно вывести на экран сообщение “Not initialized”.
    object Show: Command {
        override fun isValid(str:String): Boolean {
            if (str == "show") {
                return true
            } else return false
        }
    }
}
//— Создайте data класс Person, который представляет собой запись о человеке. Этот класс должен содержать поля:
//name – имя человека
//phone – номер телефона
//email – адрес электронной почты
data class Person(
    val name: String,
    val phone: String,
    val email: String
)
fun printHelp() {
    println("Введите команду в одном из вариантов: ")
    println("exit - выход")
    println("help - подсказка")
    println("add Имя phone +7905...")
    println("add Имя email adress@mail.com")
}
fun printPerson(person: Person) {
    println("Имя: ${person.name} Номер телефона: ${person.phone} Email: ${person.email}")
}
//— Напишите функцию readCommand(): Command, которая читает команду из текстового ввода, распознаёт её и возвращает
// один из классов-наследников Command, соответствующий введённой команде.
fun readCommand(string: String): Command {
    val myArray: List<String> = string!!.split(" ")
    val command: Command
    command = when {
        myArray[0] == "exit" -> {
            Content.Exit
        }
        myArray[0] == "help" -> {
            Content.Help
        }
        myArray[0] == "add" -> {
            Content.Add
        }
        myArray[0] == "show" -> {
            Content.Show
        }
        else -> {
            Content.Help
        }
    }
    return command
}
fun main() {
    var person = Person("0", "0", "0")
    do {
        println("Введите команду:")
        val vvod:String = readlnOrNull().toString()
        //Читаем команду с помощью функции readCommand
        val result: Command = readCommand(vvod)
        //Выводим на экран получившийся экземпляр Command
        println(result.toString())
        //Если isValid для команды возвращает false, выводим help. Если true, обрабатываем команду внутри when.
        if (result.isValid(vvod)) {
            when (result) {
                is Content -> printHelp()
                Content.Exit -> println("Goodby!")
                Content.Help -> printHelp()
                Content.Add -> {
                    val myArray: List<String> = vvod!!.split(" ")
                    when (myArray[2]) {
                        "phone" -> person = Person(myArray[1], myArray[3], "-")
                        "email" -> person = Person(myArray[1], "-", myArray[3])
                    }
                }
                Content.Show -> {
                    if (person.name == "0") {
                        println("Not initialized")
                    } else printPerson(person)
                }
            }
        } else {
            printHelp()
        }
    } while (vvod != "exit")
}


