package ast.statements;

import java.util.ArrayList;

import ast.Node;
import semanticAnalysis.Environment;
import semanticAnalysis.SemanticError;

public class AssigtStatNode extends StatementNode {
	
	final private AssigtNode ass;
	
	public AssigtStatNode(final Node ass) {
		this.ass = (AssigtNode) ass;
	}
	@Override
	public String toPrint(String indent) {
		return indent + ass.toPrint("");
	}

	@Override
	public Node typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

}
