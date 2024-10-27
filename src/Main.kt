import javax.swing.UIManager.put
import kotlin.collections.MutableList
import kotlin.collections.MutableList as MutableList1

/* //exit
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
*/
//— Измените класс Person так, чтобы он содержал список телефонов и список почтовых адресов, связанных с человеком.
//— Теперь в телефонной книге могут храниться записи о нескольких людях. Используйте для этого наиболее подходящую структуру данных.
//— Команда AddPhone теперь должна добавлять новый телефон к записи соответствующего человека.
//— Команда AddEmail теперь должна добавлять новый email к записи соответствующего человека.
//— Команда show должна принимать в качестве аргумента имя человека и выводить связанные с ним телефоны и адреса электронной почты.
//— Добавьте команду find, которая принимает email или телефон и выводит список людей, для которых записано такое значение.
sealed interface Command {
    fun isValid(str:String): Boolean
    fun printCommand(str: String)


}
class Help(): Command {
        override fun isValid(str:String): Boolean {
            if (str == "help") {
                return true
            } else return false
        }
        override fun printCommand(str: String) {
            println(str.toString())
        }
        override fun toString(): String {
            return "Help"
        }
    }
class Exit(): Command {
        override fun isValid(str:String): Boolean {
            if (str == "exit") {
                return true
            } else return false
        }
        override fun printCommand(str: String) {
            println(str)
        }
        override fun toString(): String {
            return "Exit"
        }
    }
class Add(): Command {
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
        override fun printCommand(str: String) {
            println(str)
        }
        override fun toString(): String {
            return "Add"
        }
    }
class Show(): Command {
        override fun isValid(str:String): Boolean {
            val myArray: List<String> = str!!.split(" ")
            if (myArray[0] == "show") {
                return true
            } else return false
        }
        override fun printCommand(str: String) {
            println(str)
        }
        override fun toString(): String {
            return "Show"
        }
    }
class Find(): Command {
    override fun isValid(str:String): Boolean {
        val myArray: List<String> = str!!.split(" ")
        if (myArray[0] == "find") {
            return true
        } else return false
    }
    override fun printCommand(str: String) {
        println(str)
    }
    override fun toString(): String {
        return "Find"
    }
}
data class Person(
    val name: MutableMap<String, MutableMap<String, MutableList<String>>>
)
fun printHelp() {
    println("Введите команду в одном из вариантов: ")
    println("exit - выход")
    println("help - подсказка")
    println("add Имя phone +7905...")
    println("add Имя email adress@mail.com")
    println("show Имя - для вывода информации о контактах")
    println("find Телефон или Email - поиск контакта с таким значением")
}
fun printPerson(person: Person) {
    println("Имя: " )
            //"/*${person.name} Номер телефона: ${person.phone} Email: ${person.email}")
}
//— Напишите функцию readCommand(): Command, которая читает команду из текстового ввода, распознаёт её и возвращает
// один из классов-наследников Command, соответствующий введённой команде.
fun readCommand(string: String): Command {
    val myArray: List<String> = string!!.split(" ")
    return when (myArray[0]){
        "exit" -> Exit()
        "help" -> Help()
        "add" -> Add()
        "show" -> Show()
        "find" -> Find()
        else -> Help()
    }

}

fun main() {
    var person: MutableMap<String, MutableMap<String, MutableList<String>>> = mutableMapOf(
        "Иванов" to mutableMapOf("phone" to mutableListOf("+789456123"), "email" to mutableListOf("first@email.ru")),
        "Петров" to mutableMapOf("phone" to mutableListOf("+712345678"), "email" to mutableListOf("second@email.ru")),
        "Сидоров" to mutableMapOf("phone" to mutableListOf("+789456123"), "email" to mutableListOf("first@email.ru"))
        )
    println(person)
    do {
        println("Введите команду:")
        val vvod:String = readlnOrNull().toString()
        //Читаем команду с помощью функции readCommand
        val result: Command = readCommand(vvod)
        //Выводим на экран получившийся экземпляр Command
        println("Введена команда: ${result}")
        //Если isValid для команды возвращает false, выводим help. Если true, обрабатываем команду внутри when.
        if (result.isValid(vvod)) {
            when (result) {
                is Exit -> println("Goodby!")
                is Help -> printHelp()
                //— Команда AddPhone теперь должна добавлять новый телефон к записи соответствующего человека.
                //— Команда AddEmail теперь должна добавлять новый email к записи соответствующего человека.
                is Add -> {
                    val myArray: List<String> = vvod!!.split(" ")
                    for (i in person.keys) {
                        if (i == myArray[1]) {
                            when (myArray[2]) {
                                "phone" -> {
                                    var map1: MutableMap<String, MutableList<String>> = person.getValue(myArray[1])
                                    val list1 = person.getValue(myArray[1]).getValue("phone")
                                    list1.add(myArray[3])
                                    map1.put("phone", list1)
                                    person.put(i, map1)
                                }
                                "email" -> {
                                    var map2: MutableMap<String, MutableList<String>> = person.getValue(myArray[1])
                                    val list2 = person.getValue(myArray[1]).getValue("email")
                                    list2.add(myArray[3])
                                    map2.put("email", list2)
                                    person.put(i, map2)
                                }
                            }
                        }

                    }

                }
                //— Команда show должна принимать в качестве аргумента имя человека и выводить
                // связанные с ним телефоны и адреса электронной почты.
                is Show -> {
                    val myArrayShow: List<String> = vvod!!.split(" ")
                    val name1: String = myArrayShow[1].toString()
                    println("Контакт $name1: ")
                    println(person.getValue(myArrayShow[1]))
                    }
                //— Команда show должна принимать в качестве аргумента имя человека и выводить связанные с ним
                // телефоны и адреса электронной почты.
                is Find -> {
                    val myArrayFind1: List<String> = vvod!!.split(" ")
                    val myArrayFind2: List<String> = vvod!!.split("@")
                    if (myArrayFind2.size > 1) {
                        println("Искомый email есть у контактов:")
                        val resultat = mutableListOf("")
                        for (i in person.keys) {
                            val a: Map<String, List<String>> = person.getValue(i)
                            if (a.getValue(key = "email").contains(myArrayFind1[1])) {
                                resultat.add(i)
                            }
                            println(resultat.removeAt(0))
                        }
                    } else {
                        println("Искомый номер телефона есть у контактов:")
                        val resultat = mutableListOf("")
                        for (i in person.keys) {
                            val a: Map<String, List<String>> = person.getValue(i)
                            if (a.getValue(key = "phone").contains(myArrayFind1[1])) {
                                resultat.add(i)
                            }
                            println(resultat.removeAt(0))
                        }
                    }


                }
            }
        } else {
            printHelp()
        }
    } while (vvod != "exit")
}


