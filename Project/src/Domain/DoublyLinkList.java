/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author Alán
 */
public class DoublyLinkList implements List {

    private Node first;//apunta al inicio de la lista dinamica

    public DoublyLinkList() {//Constructor
        this.first = null;//la lista todavia no existe
    }

    @Override
    public int size() throws ListException {
        if (isEmpty()) {

        }
        Node aux = first;
        int count = 0;
        while (aux != null) {
            count++;
            aux = aux.next;
        }

        return count;
    }

    @Override
    public void clear() {//Anula la lista, quita todos los valores
        first = null;
    }

    @Override
    public boolean isEmpty() {//true si la lista esta vacia
        return first == null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("List is empty : SinglyLinkedList");
        }
        Node aux = first;//el aux es apra moverme por la lita hasta el ultimo elemento
        while (aux != null) {
            if (Util.Utility.equals(aux.data, element)) {
                return true;
            }
            aux = aux.next;
        }

        return false;
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            this.first = newNode;
        } else {
            Node aux = first;//el aux es apra moverme por la lita hasta el ultimo elemento
            while (aux.next != null) {
                aux = aux.next;
            }
            //cuando se sale del while quiere decir que aux.next == null
            aux.next = newNode;
            //hago el doble enlace
            newNode.prev = aux;
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            this.first = newNode;
        }
        newNode.next = first;
        //hago doble enlace
        first.prev = newNode;
        first = newNode;

    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

   
    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("List is empty : SinglyLinkedList");
        }
        //CASO 1. EL ELEMENTO A SUPRIMIR ES EL PRIMERO DE LA LISTA
        if (Util.Utility.equals(first.data, element)) {
            first = first.next;
        } else {
            //CASO 2. EL ELEMENTO A SUPRIMIR ESTA EN CUALQUIER OTRA POSICION
            Node prev = first; //esto es para dejar rastro, apunta al anterior de aux
            Node aux = first.next;
            System.out.println("Hola");
            while (aux != null && !Util.Utility.equals(aux.data, element)) {
                prev = aux; //un nodo atras de aux
                aux = aux.next;
            }
            //sale del while cuando alcanza null o cuando encuentra el elemento a suprimir
            if (aux != null && Util.Utility.equals(aux.data, element)) {
                //desenlazo el nodo
                prev.next = aux.next;
            }
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("List is empty : SinglyLinkedList");
        }
        Object element = first.data;
        first = first.next;//muevo el apuntaador al siguiente nodo
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("List is empty : SinglyLinkedList");
        }
        Node aux = first;
        Node prev = first; //esto es para dejar rastro, apunta al anterior de aux
        while (aux.next != null) {
            prev = aux; //un nodo atras de aux
            aux = aux.next;
        }
        //se sale del while cuando esta en el ultimo nodo
        Object element = aux.data;
        prev.next = null;//desconecto el ultimo nodo
        return element;
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("List is empty : SinglyLinkedList");
        }
        Node aux = first;
        int index = 1; // el primer nodo estara en el indice 1
        while (aux != null) {
            if (Util.Utility.equals(aux.data, element)) {
                return index; // ya lo encontro
            }
            index++;
            aux = aux.next;
        }
        return -1; // significa que el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("List is empty : SinglyLinkedList");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("List is empty : SinglyLinkedList");
        }
        Node aux = first;
        while (aux.next != null) {
            aux = aux.next;
        }
        return aux.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {

        return element;
    }

    @Override
    public Object getNext(Object element) throws ListException {

        return element;
    }

    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("List is empty");
        }
        Node aux = this.first;
        int cont = 1;
        while (cont < index) {
            cont++;
            aux = aux.next;
        }
        if (cont == index) {
            return aux;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        String result = "Doubly Linked List\n";
        Node aux = first;//el aux es apra moverme por la lita hasta el ultimo elemento
        while (aux != null) {
            result += aux.data + " \n ";
            aux = aux.next;
        }
        return result;
    }

}
