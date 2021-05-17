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
public class CircularLinkList implements List {

    private Node first;//apunta al inicio de la lista dinamica    
    private Node last; // apunta al nodo final de la lista

    public CircularLinkList() {//Constructor
        this.first = null;
        this.last = null;//la lista todavia no existe
    }

    @Override
    public int size() throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        Node aux = first;
        int count = 0;
        while (aux != last) {
            count++;
            aux = aux.next;
        }
        //Se sale cuando esta en el ultimo nodo
        return count + 1;// +1 para que cuente el ultimo nodo
    }

    @Override
    public void clear() {//Anula la lista, quita todos los valores
        this.first = this.last = null;
    }

    @Override
    public boolean isEmpty() {//true si la lista esta vacia
        return first == null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        Node aux = first;//El aux es para moverme por la lita hasta el ultimo elemento
        while (aux != last) {
            if (Util.Utility.equals(aux.data, element)) {
                return true;
            }
            aux = aux.next;
        }
        //Se sale cuando aux == last, en este caso solo resta verificar si el elemento a buscar esta en ese nodo
        return Util.Utility.equals(aux.data, element);
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            this.first = this.last = newNode;
        } else {
            last.next = newNode;
            last = newNode;// muevo last a que apunte al ultimo nodo

            //Hago el enlace circular
            last.next = first;
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            this.first = this.last = newNode;
        }
        newNode.next = first;
        first = newNode;

        //Hago enlace circular
        last.next = first;
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        //CASO 1. EL ELEMENTO A SUPRIMIR ES EL PRIMERO DE LA LISTA
        if (Util.Utility.equals(first.data, element)) {
            first = first.next;
        } else {
            //CASO 2. EL ELEMENTO A SUPRIMIR ESTA EN CUALQUIER OTRA POSICION
            Node prev = first; //esto es para dejar rastro, apunta al anterior de aux
            Node aux = first.next;

            while (aux != last && !Util.Utility.equals(aux.data, element)) {
                prev = aux; //un nodo atras de aux
                aux = aux.next;
            }
            //Se sale cuano aux == last o cuando encuentra el elemento a suprimir
            if (Util.Utility.equals(aux.data, element)) {
                //Desenlazo el nodo
                prev.next = aux.next;
            }
            //Debo asegurarme que las apunte al ultimo nodo
            if (aux == last && Util.Utility.equals(aux.data, element)) {//Estamos en el ultimo nodo
                last = prev;
            }
        }
        //Mantengo enlace ciruclar
        this.last.next = first;

        //Que pasa si solo queda un nodo y es el que quiero eliminar?
        if (first == last && Util.Utility.equals(first.data, element)) {
            clear();//En este caso anulamos la lista
        }

    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        Object element = first.data;
        first = first.next;//Muevo el apuntaador al siguiente nodo
        //Mantengo el enlace circular
        last.next = first;
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        Node aux = first;
        Node prev = first; //Esto es para dejar rastro, apunta al anterior de aux
        while (aux.next != last) {
            prev = aux; //Un nodo atras de aux
            aux = aux.next;
        }
        //Se sale del while cuando esta en el ultimo nodo
        Object element = aux.data;
        //prev.next = first;//Desconecto el ultimo nodo
        last = prev;

        //Mantengo el enlace circular
        last.next = first;

        return element;
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        Node aux = first;
        int index = 1; // El primer nodo estara en el indice 1
        while (aux != last) {
            if (Util.Utility.equals(aux.data, element)) {
                return index; //Ya lo encontro
            }
            index++;
            aux = aux.next;
        }
        //Sale cuando el aux == last
        if (Util.Utility.equals(aux.data, element)) {
            return index;
        }
        return -1; //Significa que el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        Node aux = first;
        while (aux.next != null) {
            aux = aux.next;
        }
        return last.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {//HACER NOSOTROS

        return element;
    }

    @Override
    public Object getNext(Object element) throws ListException {//HACER NOSOTROS

        return element;
    }

    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("CircularLinkedList: Vacia");
        }
        Node aux = this.first;
        int cont = 1;
        while (aux != last) {
            if (Util.Utility.equals(cont, index)) {
                return aux;
            }
            cont++;
            aux = aux.next;
        }
        if (Util.Utility.equals(cont, index)) {
            return aux;
        }
        
        return null;

    }

    @Override
    public String toString() {
        String result = "Circular Linked List\n";
        Node aux = first;//el aux es apra moverme por la lita hasta el ultimo elemento
        while (aux != last) {
            result += aux.data + "\n";
            aux = aux.next;
        }
        return result += aux.data;
    }

}
