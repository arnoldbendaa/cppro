// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.WorksheetColumnMapping;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaLexer;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaParser$translation_unit_return;
import com.cedar.cp.util.flatform.model.parser.ExcelFormulaTreeParser;
import com.cedar.cp.util.flatform.model.parser.ParserTest$1;
import com.cedar.cp.util.flatform.model.parser.WorksheetFormulaExecutor;
import java.util.HashSet;
import java.util.Set;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class ParserTest {

   public void testFormulaOne() throws Exception {
      ANTLRStringStream input = new ANTLRStringStream("=IF(A1<A2,SUM(A1:A3,A1,A2,A3),IF(A3=A3,A3/A3,A3*A3))");
      ExcelFormulaLexer lexer = new ExcelFormulaLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      ExcelFormulaParser parser = new ExcelFormulaParser(tokens);
      ExcelFormulaParser$translation_unit_return r = parser.translation_unit();
      CommonTree tree = (CommonTree)r.getTree();
      Set cellRefs = this.queryCellRefs(tree, new ParserTest$1(this));
      System.out.println("CellRefs:" + cellRefs.size());
      CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
      ExcelFormulaTreeParser treeParser = new ExcelFormulaTreeParser(nodes);
      WorksheetFormulaExecutor fe = new WorksheetFormulaExecutor();
      treeParser.setExecutor(fe);
      treeParser.translation_unit();
      System.out.println("Result:" + treeParser.pop());
   }

   private Set<CellRef> queryCellRefs(CommonTree tree, WorksheetColumnMapping colMap) {
      HashSet cellRefs = new HashSet();
      this.queryCellRefs(tree, cellRefs, colMap);
      return cellRefs;
   }

   private void queryCellRefs(CommonTree tree, Set<CellRef> cellRefs, WorksheetColumnMapping colMap) {
      if(tree.getToken() != null && tree.getToken().getType() == 12) {
         CellRef var7 = new CellRef();

         for(int i1 = 0; i1 < tree.getChildCount(); ++i1) {
            CommonTree child = (CommonTree)tree.getChild(i1);
            if(child.getToken().getType() == 26) {
               var7.setColumn(colMap.getColumn(child.getToken().getText()));
            } else if(child.getToken().getType() == 27) {
               var7.setRow(Integer.parseInt(child.getToken().getText()));
            } else if(i1 == 0) {
               var7.setColumnAbsolute(true);
            } else {
               var7.setRowAbsolute(true);
            }
         }

         cellRefs.add(var7);
      } else {
         for(int i = 0; i < tree.getChildCount(); ++i) {
            this.queryCellRefs((CommonTree)tree.getChild(i), cellRefs, colMap);
         }
      }

   }
}
