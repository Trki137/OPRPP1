package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimListModelTest {

  @Test
  public void testNext(){
    PrimListModel primListModel = new PrimListModel();
    List<Integer> listFirst20Prim = new ArrayList<>(
        List.of(1, 2, 3,
            5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67));

    for(int i = 0; i < 19; i++) primListModel.next();

    Assertions.assertEquals(listFirst20Prim,primListModel.getPrimList());
  }
}
