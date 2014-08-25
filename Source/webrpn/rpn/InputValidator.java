package webrpn.rpn;

import java.util.regex.Pattern;

import webrpn.dependencies.HelpToken;

import com.google.inject.Inject;

public class InputValidator 
{
	private final String helpToken;
	private final Pattern whitespacePattern = Pattern.compile("\\s+");
	
	@Inject
	public InputValidator(@HelpToken String helpToken)
	{
		if (helpToken == null)
		{
			throw new NullPointerException("helpToken cannot be null.");
		}

		this.helpToken = helpToken.toLowerCase();
	}
	
	public InputResult validate(String[] input) 
	{
		StringBuilder inputStringBuilder = new StringBuilder();
		for (String field : input)
		{
			if (!isNullOrWhitespace(field))
			{
				inputStringBuilder.append(field);
			}
		}
		String inputString = inputStringBuilder.toString();
		
		InputResult result = new InputResult();
		
		if (isNullOrWhitespace(inputString))
		{
			result.setError("Empty input.");
		}
		else if (containsHelp(inputString))
		{
			result.setIsHelp();
		}
		else
		{
			result.setValidatedInput(input);
		}
		
		return result;
	}

	private boolean isNullOrWhitespace(String rawInput) 
	{
		if (rawInput == null)
		{
			return true;
		}
		
		boolean isEmpty = "".equals(rawInput);
		boolean isWhiteSpace = whitespacePattern.matcher(rawInput).matches();
		
		return isEmpty || isWhiteSpace;
	}

	private boolean containsHelp(String rawInput) 
	{
		return rawInput.toLowerCase().contains(helpToken);
	}
}
