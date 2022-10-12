package hr.fer.oprpp1.custom.collections;

import java.nio.charset.UnsupportedCharsetException;

public class LinkedListIndexedCollection extends Collection {

    private int size;
    private ListNode firstNode;
    private ListNode lastNode;

    public LinkedListIndexedCollection(){
        this.size = 0;
        this.firstNode = this.lastNode = null;
    }
    public LinkedListIndexedCollection(Collection collection){

        addAll(collection);
    }

    public Object get(int index){
        if(index < 0 || index > this.size - 1) throw  new IndexOutOfBoundsException();

        ListNode node = firstNode;
        int i = 0;

        while (node != null){
            if(i == index) return node.value;
            node = node.nextNode;
            i++;
        }

        return null;
    }

    public void insert(Object value, int position){
        if(position < 0 || position > size) throw new IllegalArgumentException();
        if(value == null) throw new NullPointerException();
        ListNode insertedNode = new ListNode(null,null,value);
        ListNode node = firstNode;
        int i = 0;


        if(position == size) add(value);
        else if(position == 0){
            firstNode.prevNode = insertedNode;
            insertedNode.nextNode = firstNode;
            firstNode = insertedNode;
            size++;
        } else {
            boolean finished = false;
            while (node != null && !finished){

                if(position == i){

                    node.prevNode.nextNode = insertedNode;
                    insertedNode.prevNode = node.prevNode;
                    node.prevNode = insertedNode;
                    insertedNode.nextNode = node;

                    size++;
                    finished = true;
                }
                i++;
                node = node.nextNode;
            }

        }




    }

    public int indexOf(Object value){
        if(value == null) return -1;

        int foundPosition = -1;
        int i = 0;
        ListNode node = firstNode;

        while (node != null && foundPosition == -1){
            if(node.value.equals(value)) foundPosition = i;
            node = node.nextNode;
            i++;
        }

        return foundPosition;
    }
    public boolean remove(int index){
        if(index < 0 || index > size - 1) throw new IllegalArgumentException();

        if(index == 0) {
            firstNode = firstNode.nextNode;
            firstNode.prevNode = null;
        }else if(index == size - 1){
            lastNode = lastNode.prevNode;
            lastNode.nextNode = null;
        }else {
            ListNode node = firstNode;
            int i = 0;
            boolean found = false;
            while(node != null && !found){
                if(i == index){
                    node.prevNode.nextNode = node.nextNode;
                    node.nextNode.prevNode = node.prevNode;

                    node.prevNode = node.nextNode = null;
                    found = true;
                }
                node = node.nextNode;
                i++;
            }
        }

        size--;
        return true;
        }


    @Override
    public int size(){
        return this.size;
    }

    @Override
    public void add(Object value){
        if(value == null) throw new NullPointerException();

        ListNode newNode = new ListNode(lastNode,null,value);

        if(firstNode == null)
            firstNode = newNode;
        else
            this.lastNode.nextNode = newNode;

        this.lastNode = newNode;
        //System.out.println(newNode.prevNode +" , "+ newNode.nextNode + " , " + value);
        size++;
    }

    @Override
    public boolean contains(Object value){
        ListNode node = firstNode;

        while(node != null){
            if(node.value.equals(value)) return true;
            node = node.nextNode;
        }

        return false;
    }

    @Override
    public boolean remove(Object value){

        int index = indexOf(value);
        if(index == -1) return false;

        remove(index);
        return true;
    }

    @Override
    public Object[] toArray(){

        ListNode node = this.firstNode;
        Object[] arr = new Object[this.size];
        int i = 0;

        while(node != null){
            arr[i] = node.value;
            node = node.nextNode;
            i++;
        }

        return arr;
    }

    @Override
    public void forEach(Processor processor){
        ListNode node = firstNode;

        while(node != null){
            processor.process(node.value);
            node = node.nextNode;
        }
    }

    @Override
    public void clear(){
        firstNode = lastNode = null;
        size = 0;
    }



    private static class ListNode{
        private ListNode prevNode;
        private ListNode nextNode;
        Object value;

        public ListNode(ListNode prevNode, ListNode nextNode, Object value){
            this.prevNode = prevNode;
            this.nextNode = nextNode;
            this.value = value;
        }


    }

}
