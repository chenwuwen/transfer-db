package transfer;

public class CompleteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String msg;

	public CompleteException(String msg) {
		this.msg = msg;
	}

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		System.out.println("CompleteException [msg=" + msg + "]");
	}

}
