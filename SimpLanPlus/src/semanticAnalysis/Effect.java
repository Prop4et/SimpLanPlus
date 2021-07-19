package semanticAnalysis;

public class Effect {
	//bottom
	public static final int BOT = 0;
	//rw
	public static final int RW = 1;
	//delete
	public static final int DEL = 2;
	//top
	public static final int TOP = 3;
	
	private int type;
	
	public Effect(int type) {
		this.type=type;
	}
	
	public Effect() {
		this.type = BOT;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type=type;
	}
	
	public Effect max(Effect e1, Effect e2) {
		return new Effect(Math.max(e1.getType(), e2.getType()));
	}
	
	public Effect seq(Effect e1, Effect e2) {
		int typeMax = max(e1, e2).getType();
		if (typeMax <= DEL) {
			return new Effect(typeMax);
		}
		if ((e1.getType() <= RW) || (e1.getType() == DEL && e2.getType() == BOT))
			return new Effect(DEL);
		return new Effect(TOP);
	}
	
	public Effect par(Effect e1, Effect e2) {
		if(e2.getType() == BOT)
			return e1;
		if(e1.getType() == BOT)
			return e2;
		if(e1.getType() == RW && e2.getType() == RW)
			return new Effect(RW);
		return new Effect(TOP);
	}
}
