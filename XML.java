class XML{ 
{
	public static final Character AMP = new Character('&');
	public static final Character APOS = new Character('\'');
	public static final Character BANG = new Character('!');
	public static final Character EQ = new Character('=');
	public static final Character GT = new Character('>');
	public static final Character LT = new Character('<');
	public static final Character QUEST = new Character('?');
	public static final Character QUOT = new Character('"');
	public static final Character SLASH = new Character('/');

	public static String escape(String string){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < string.length(); i ++){
			char c = string.charAt(i);
			switch(c){
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static void noSpace(String string)throws JSONException{
		int i;
		int length = string.length();
		if (length == 0)
		{
			throw new JSONException("Empty string.");
		}
		for (i = 0; i < length ; i ++ )
		{
			if (Character.isWhitespace(string.charAt(i)))
			{
				throws new JSONException("'" + string + "' contains a space character.");
			}
		}
	}

	private  static boolean parse(XMLTokener x, JSONObject context, String name)throws JSONException{
		char c;
		int i;
		JSONObject jsonobject = null;
		String string;
		String tagName;
		Object token;

		if (token == BANG)
		{
			c= x.next();
			if (c == '-')
			{
				if (x.next() == '-')
				{
					x.skipPast("-->");
					return false;
				}
				x.back();
			}else if (c == '[')
			{
				token = x.nextToken();
				if ("CDATA".equals(token))
				{
					if (x.next() == '[')
					{
						string = x.nextCDATA();
						if (string.length() > 0)
						{
							context.accumulate("content", string);
						}
						return false;
					}
				}
				throw x.syntaxError("Expected 'CDATA['");
			}
			i = 1;
			do
			{
				token = x.nextMeta();
				if (token == null)
				{
					throw x.syntaxError("Missing '>' after '<!'.");
				}else if (token == LT)
				{
					i += 1;
				}else if (token == GT)
				{
					i -= 1;
				}
			}
			while ( i > 0);
			return false;
		}else if ( token == QUEST)
		{
			x.skipPast("?>");
			return false;
		}else if (token == SLASH)
		{
			token = x.nextToken();
			if (name == null)
			{
				throw x.syntaxError("Mismatched close tag" + token);
			}
			if (!token.equals(name))
			{
				throw x.syntaxError("Mismatched" + name + "and" + token);
			}
			if (x.nextToken() != GT)
			{
				throw x.syntaxError("Misshaped close tag");
			}
			return true;
		}else if (token instanceof Character)
		{
			throw x.syntaxError("Misshaped tag");
		}else {
			tagName = (String)token;
			token = null;
			jsonobject = new JSONObject();
			for (; ; )
			{
				if (token == null)
				{
					token = x.nextToken();
				}
				if (token instanceof String)
				{
					string = (String)token;
					token = x.nextToken();
					if (token == EQ)
					{
						token = x.nextToken();
						if (!(token instanceof String))
						{
							throw x.syntaxError("Missing value");
						}
						jsonobject.accumulate(string, XML.stringToValue((String)token));
						token = null;
					}else {
						jsonobject.accumulate(string, "");
					}
				}else if (token == SLASH)
				{
					if (x.nextToken() != GT)
					{
						throw x.syntaxError("Missshaped tag");
					}
					if (jsonobject.length() > 0)
					{
						context.accumulate(tagName, jsonobject);
					}else {
						context.accumulate(tagName, "");
					}
					return false;
				}else if (token == GT)
				{
					for (; ; )
					{
						token = x.nextContent();
						if (token == null)
						{
							if (tagName != null)
							{
								throw x.syntaxError("Unclosed tag" + tagName);
							}
							return false;
						}else if (token instanceof String)
						{
							string = (String)token;
							if (string.length() > 0)
							{
								jsonobject.accumulate("content", XML.stringToValue(string));
							}
						}else if (token == LT)
						{
							if (parse(x, jsonobject, tagName))
							{
								if (jsonobject.length() == 0)
								{
									context.accumulate(tagName, "");
								}else if (jsonobject.length() == 1 && jsonobject.opt("content") != null)
								{
									context.accumulate(tagName, jsonobject.opt("content"));
								}else{
									context.accumulate(tagName, jsonobject);
								}
								return false;
							}
						}
					}
				}else {
					throw x.syntaxError("Misshaped tag");
				}
			}
		}
	}


	public static void main(String[] args) 
	{
	}
}
