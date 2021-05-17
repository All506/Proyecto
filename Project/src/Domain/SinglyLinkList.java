package Domain;
//Lista enlazada 

//Implementa la interfaz Lista
public class SinglyLinkList implements List{

    //Se crean los atributos que necesita la clase para funcionar
    private Node first; //Apunta al inicio de la lista dinámica
    
    //Constructor 
    
    public SinglyLinkList(){
        this.first = null; //La lista a este punto aún no existe 
    }
    
    @Override //Implementa un método que viene de la interfaz List
    public int size() throws ListException {
       if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        }
       Node aux = first;
       int count = 0;
       while(aux!=null){
           count++;
           aux = aux.next;
       }
       return count;
    }

    @Override
    public void clear() {
        this.first = null; //Anula todos los nodos linkeados que existan en la lista
    }

    @Override
    public boolean isEmpty() {
        return first == null; //Retorna verdadero si el primer nodo está vacio
    }

    @Override
    public boolean contains(Object element) throws ListException {
        //Debe de devolver true si el objeto existe en la lista
        if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        } else {
            Node aux = first; 
            while(aux!=null){ //Va a analizar incluso el primer elemento para ver si es igual al objeto
                if(Util.Utility.equals(aux.data, element)){
                    return true;
                }
                aux = aux.next;
            }
            return false; //El elemento no existe
        }
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()){
            this.first = newNode;
        } else {
            Node aux = first; //Auxiliar se arranca en first para moverse a través de la lista hasta el último elemento
            while(aux.next!=null){
                aux = aux.next;
            }
            //Una vez se sale del while, quiere decir que aux.next es == null
            aux.next = newNode;
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()){
            this.first = newNode;
        }
        newNode.next = first;
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }



    @Override
    public void remove(Object element) throws ListException {
         if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        }
        //CASO 1. Elemento es el primero
        if (Util.Utility.equals(first.data, element)){
            first = first.next; //Se mueve el apuntador al siguiente nodo
        }
        //CASO 2. Elemento a suprimir esta en cualquier otra posicion
         Node aux = first; 
         Node prev = first; //Lleva el rastro del anterior elemento en el while
        while(aux!=null&&!Util.Utility.equals(aux.data, element/*MIENTRAS NO SEA QUIEN BUSCA*/)){ //Se sale del while cuando esta en el ultimo nodo
                prev = aux; //Se mantiene un nodo atrás del auxiliar
                aux = aux.next;
        }
        //Se sale del while cuando alcanza nulo o encuentra el elemento
        if (aux!=null&&Util.Utility.equals(aux.data, element)){
            //Desenlaza o desconecta el nodo
            prev.next = aux.next;
        }
    }

    @Override
    public Object removeFirst() throws ListException { //Suprime y retorna primer elemento de la lists
        if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        }
        Object element = first.data;
        first = first.next;
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        } else {
            Node aux = first; 
            Node prev = first; //Lleva el rastro del anterior elemento en el while
            while(aux.next!=null){ //Se sale del while cuando esta en el ultimo nodo
                prev = aux; //Se mantiene un nodo atrás del auxiliar
                aux = aux.next;
            }
            Object element = aux.data;
            prev.next = null; //Se desconecta el ultimo nodo
            return element; //El elemento no existe
        }
    }


    @Override
    public int indexOf(Object element) throws ListException {
         if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        } else {
            Node aux = first; 
            int index = 1; //El primer nodo está en el índice 1
            while(aux!=null){ //Se sale del while cuando esta en el ultimo nodo
                if (Util.Utility.equals(aux.data, element)){
                    return index;
                }
                index++;
                aux = aux.next;
            }
        }
         return -1; //Significa que el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
         if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        } 
             return first.data;
    }

    @Override
    public Object getLast() throws ListException {
         if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        } else {
            Node aux = first; 
            while(aux.next!=null){ 
                aux = aux.next;
            }
            return aux.data; //El elemento no existe
        }     
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        } else {
            Node aux = first;
            while(aux.next!=null){ //Se sale del while cuando esta en el ultimo nodo
                if (Util.Utility.equals(aux.next.data, element)){ //Compara el siguiente elemento del nodo con el objeto enviado
                    return aux.data;
                }
                aux = aux.next; //Continua el ciclo
            }
        }
        return null;
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        } else {
            Node aux = first;
            while(aux.next!=null){ //Se sale del while cuando esta en el ultimo nodo
                if (Util.Utility.equals(aux.data, element)){
                    return aux.next.data;
                }
                aux = aux.next; //Continua el ciclo
            }
        }
        return null;
    }

    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()){ //Si la lista esta vacia
            throw new ListException("List is empty");
        } else {
            int i = 1; //El indice del primer elemento de la lista
            Node aux = first; 
            while(aux!=null){ //Se sale del while cuando esta en el ultimo nodo
                if (Util.Utility.equals(i, index)){
                    return aux;
                }
                i++;
                aux = aux.next;
            }
            return null;
        }
    }

    @Override
    public String toString() {
        String result = "SINGLY LINK LIST \n";
            Node aux = first; //Auxiliar se arranca en first para moverse a través de la lista hasta el último elemento
            while(aux!=null){
                result += aux.data + "\n";
                aux = aux.next;
            }
        return result;
    }
    
}
