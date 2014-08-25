package webrpn.rpn;

public class InputResult 
{
	private String[] validInput; 
	private String error;
	private boolean isHelp;
	private boolean valid;
	
	public String[] getValidatedInput()
	{
		return validInput;
	}
	
	public void setValidatedInput(String[] input) 
	{
		validInput = input;
		setValid(true);
	}
	
	public String getError()
	{
		return error;
	}
	
	public void setError(String errorMessage) 
	{
		this.error = errorMessage;
		setValid(false);
	}

	public boolean isHelpRequest() 
	{
		return isHelp;
	}

	public void setIsHelp() 
	{
		this.isHelp = true;
		setValid(false);
	}
	
	public boolean isValidInput() 
	{
		return valid;
	}

	private void setValid(boolean valid) 
	{
		this.valid = valid;	
	}
}
