public class BSTMultiSet extends MultiSet {

    // a multiset always starts empty, so we can directly instantiate our private attribute
    // here; no need to explicitly write a new constructor.
    private final BST bst = new BST();
    @Override
    public void add(Integer item) {
        bst.insert(item);
    }

    @Override
    public void remove(Integer item) {
        bst.delete(item);
    }

    @Override
    public boolean contains(Integer item) {
        return bst.contains(item);
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    @Override
    public int count(Integer item) {
        return bst.count(item);
    }

    @Override
    public int size() {
        return bst.getLength();
    }

    // TODO Task: hover the red squiggly on the first line and select 'Implement methods'.
    //            All listed methods should be selected. Press okay and then implement each
    //            method. As with the python version, this shouldn't require a lot of code to write.
    //
    //            Hint: autocomplete will help as you look for the right bst methods to call,
    //                  but make sure to double-check that you are calling the correct methods!
    //
    //.           Note: if the red squiggly line isn't there, make sure src is marked as
    //                  the sources root; if it still isn't there, you will just need to manually
    //                  copy over the method headers from MultiSet.java to be implemented.

}
