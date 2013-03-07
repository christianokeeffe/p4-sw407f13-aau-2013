<?php
	if($_POST["bnf_output"] == "two")
	{
	
		$input_bnf = $_POST["bnf_input"];
		
		
		$input_bnf = str_replace("\"<--\"", "ARROW", $input_bnf);
		$input_bnf = str_replace("\"<\"", "LESSTHAN", $input_bnf);
		$input_bnf = str_replace("\">\"", "GREATERTHAN", $input_bnf);
		$input_bnf = str_replace("endcase", "ENDCASEPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace("factor", "FPLACEHOLDER", $input_bnf);

		$input_bnf = str_replace("\"", "", $input_bnf);
		
		$input_bnf = str_replace(";", " \";\" ", $input_bnf);
		$input_bnf = str_replace(")", " \")\" ", $input_bnf);
		$input_bnf = str_replace("(", " \"(\" ", $input_bnf);
		$input_bnf = str_replace("[ ]", " \"[ ]\" ", $input_bnf);
		$input_bnf = str_replace(",", " \",\" ", $input_bnf);
		$input_bnf = str_replace("!=", "NOTEQUAL", $input_bnf);
		$input_bnf = str_replace("!", " \"!\" ", $input_bnf);
		$input_bnf = str_replace("=", " \"=\" ", $input_bnf);
		$input_bnf = str_replace("+", " \"+\" ", $input_bnf);

		$input_bnf = str_replace("*", " \"*\" ", $input_bnf);
		$input_bnf = str_replace("/", " \"/\" ", $input_bnf);



		$input_bnf = str_replace("<", "", $input_bnf);
		$input_bnf = str_replace(">", "", $input_bnf);
		$input_bnf = str_replace("$\\rightarrow$", " :", $input_bnf);
		$input_bnf = str_replace("$\\ra$", " :", $input_bnf);
		$input_bnf = str_replace(": [", "= [", $input_bnf);
		
		$input_bnf = str_replace("[a - zA - Z]", "LETTERPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace("[0 - 9]", "DIGITPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace("\">=\"", "GREATERTHANOREQUAL", $input_bnf);
		$input_bnf = str_replace("\"<=\"", "LESSTHANOREQUAL", $input_bnf);
		$input_bnf = str_replace("-", " \"-\" ", $input_bnf);
		
		//Remove epsilons
		$input_bnf = str_replace("$\\varepsilon$", "", $input_bnf);
		$input_bnf = str_replace("$\\epsilon$", "", $input_bnf);
		$input_bnf = str_replace("$\\eps$", "", $input_bnf);
		$input_bnf = htmlspecialchars($input_bnf);
		
		$input_bnf = str_replace("\n", "<br />", $input_bnf);
		$input_bnf = str_replace("\alt", "|", $input_bnf);

		$input_bnf = str_replace("GREATERTHANOREQUAL", "\">=\"", $input_bnf);
		$input_bnf = str_replace("LESSTHANOREQUAL", "\"<=\"", $input_bnf);	
		$input_bnf = str_replace("GREATERTHAN", "\">\"", $input_bnf);

		$input_bnf = str_replace("LESSTHAN", "\"<\"", $input_bnf);
		$input_bnf = str_replace("ARROW", "\"<--\"", $input_bnf);
		$input_bnf = str_replace("NOTEQUAL", "\"!=\"", $input_bnf);
		$input_bnf = str_replace("LETTERPLACEHOLDER", "[a-zA-Z]", $input_bnf);
		$input_bnf = str_replace("DIGITPLACEHOLDER", "[0-9]", $input_bnf);
		$input_bnf = str_replace("''", "\"", $input_bnf);
		
		//SYMBOLS
		
		$input_bnf = str_replace("| \%", "| \"%\"", $input_bnf);
		$input_bnf = str_replace("| \^", "| \"^\"", $input_bnf);
		$input_bnf = str_replace("| \&", "| \"&\"", $input_bnf);
		$input_bnf = str_replace("amp;", "", $input_bnf); 
		$input_bnf = str_replace("| \_", "| \"_\"", $input_bnf);
		$input_bnf = str_replace("| |", "| \"|\"", $input_bnf);
		$input_bnf = str_replace("| \~", "| \"~\"", $input_bnf);
		$input_bnf = str_replace("| \`", "| \"`\"", $input_bnf);
		$input_bnf = str_replace("| \{", "| \"{\"", $input_bnf);
		$input_bnf = str_replace("| \}", "| \"}\"", $input_bnf);
		$input_bnf = str_replace("| ]", "| \"]\"", $input_bnf);
		$input_bnf = str_replace("| [", "| \"[\"", $input_bnf);
		$input_bnf = str_replace("| :", "| \":\"", $input_bnf);
		$input_bnf = str_replace("| ?", "| \"?\"", $input_bnf);
		$input_bnf = str_replace("| .", "| \".\"", $input_bnf);
		$input_bnf = str_replace("| ' '", "| \" \"", $input_bnf);
		$input_bnf = str_replace("| '", "| \"'\"", $input_bnf);
		
		//RESERVED
		$input_bnf = str_replace("bool", "\"bool\"", $input_bnf);
		$input_bnf = str_replace("int", "\"int\"", $input_bnf);
		$input_bnf = str_replace("double", "\"double\"", $input_bnf);
		$input_bnf = str_replace("char", "\"char\"", $input_bnf);
		$input_bnf = str_replace("string", "\"string\"", $input_bnf);
		$input_bnf = str_replace(" OR ", " \"OR\" ", $input_bnf);
		$input_bnf = str_replace(" AND ", " \"AND\" ", $input_bnf);
		$input_bnf = str_replace("true", "\"true\"", $input_bnf);
		$input_bnf = str_replace("false", "\"false\"", $input_bnf);
		$input_bnf = str_replace("begin", "\"begin\"", $input_bnf);
		$input_bnf = str_replace("end", "\"end\"", $input_bnf);
		$input_bnf = str_replace("if", "\"if\"", $input_bnf);
		$input_bnf = str_replace("else", "\"else\"", $input_bnf);
		$input_bnf = str_replace("function ", "\"function\" ", $input_bnf);
		
		$input_bnf = str_replace("returns", "\"returns\"", $input_bnf);
		$input_bnf = str_replace("return ", "\"return\" ", $input_bnf);
		$input_bnf = str_replace("nothing", "\"nothing\"", $input_bnf);
		$input_bnf = str_replace("switch", "\"switch\"", $input_bnf);
		$input_bnf = str_replace("break", "\"break\"", $input_bnf);
		$input_bnf = str_replace("default:", "\"default:\"", $input_bnf);
		$input_bnf = str_replace("from", "\"from\"", $input_bnf);
		$input_bnf = str_replace(" to ", " \"to\" ", $input_bnf);
		$input_bnf = str_replace("step", "\"step\"", $input_bnf);
		$input_bnf = str_replace("while", "\"while\"", $input_bnf);
		$input_bnf = str_replace("ENDCASEPLACEHOLDER", "endcase", $input_bnf);
		$input_bnf = str_replace("FPLACEHOLDER", "factor", $input_bnf);
		
		
		//FIX
		$input_bnf = str_replace(": \"function\"", ": function", $input_bnf);
		$input_bnf = str_replace("| \"function\"", "function :", $input_bnf);
		$input_bnf = str_replace("\"function\"  :", "function :", $input_bnf);
		$input_bnf = str_replace("\"string\"  :", "string :", $input_bnf);
		$input_bnf = str_replace("\"from\"  :", "from :", $input_bnf);
		$input_bnf = str_replace("\"switch\"  :", "switch :", $input_bnf);
		$input_bnf = str_replace("\"while\"  :", "while :", $input_bnf);
		$input_bnf = str_replace("\"if\"  :", "if :", $input_bnf);
		$input_bnf = str_replace("\"end\"if :", "endif :", $input_bnf);
		$input_bnf = str_replace("\"bool\"eanoperand  :", "booleanoperand :", $input_bnf);
		$input_bnf = str_replace("\"bool\"ean", "boolean", $input_bnf);
		$input_bnf = str_replace("| \"switch\"", "| switch", $input_bnf);
		echo "$input_bnf";
	}
	else if($_POST["bnf_output"] == "three")
	{
		$input_bnf = $_POST["bnf_input"];
		$array_lines = explode("\n", $input_bnf);
		include_once "header.php";
		?>
			<div id="selectable" onclick="selectText('selectable')">
		<?php
		foreach($array_lines as $line)
		{
		
			$exist = strpos($line, "end{grammar}");
			
			if($exist != false)
			{
				$line= "";
			}
			
			$exist = strpos($line, "begin{grammar}");
			
			if($exist != false)
			{
				$line= "";
			}
			
			if($line[0] == "%")
			{
				$line = "";
			}
		
			$chk = 0;
			$line = str_replace("$\\ra$", " ->", $line);
	
			
			$line = str_replace("\\alt", "|", $line);
			
			//Symbols
			$line = str_replace(";", " ';'", $line);
			$line = str_replace("\"", "'", $line);
			$line = str_replace("(", "'(' ", $line);
			$line = str_replace(")", " ')'", $line);
			$line = str_replace(",", " ','", $line);
			$line = str_replace("'<''-''-'", "'<--'", $line);
			$line = str_replace("+", " '+' ", $line);
			$line = str_replace("*", " '*' ", $line);
			$line = str_replace("/", " '/' ", $line);
			$line = str_replace("-<", " '-' ", $line);
			$line = str_replace(".", "'.'", $line);
			
			$line = str_replace("| \%", "| '%'", $line);
			$line = str_replace("| \^", "| '^'", $line);
			$line = str_replace("| \&", "| '&'", $line);
			$line = str_replace("amp;", "", $line); 
			$line = str_replace("| \_", "| '_'", $line);
			$line = str_replace("| |", "| '|'", $line);
			$line = str_replace("| \~", "| '~'", $line);
			$line = str_replace("| \`", "| '`'", $line);
			$line = str_replace("| \{", "| '{'", $line);
			$line = str_replace("| \}", "| '}'", $line);
			$line = str_replace("| ]", "| ']'", $line);
			$line = str_replace("| [", "| '['", $line);
			$line = str_replace("| =", "| '='", $line);
			$line = str_replace(":", "':'", $line);
			$line = str_replace("| ?", "| '?'", $line);
			$line = str_replace("!", "'!' ", $line);
			
			$line = str_replace("[a - zA - Z]", "a | ... | z | A | ... | Z", $line);
			$line = str_replace("[0 - 9]", "0 | ... | 9", $line);
			$line = str_replace("[1 - 9]", "1 | ... | 9", $line);


			$line = str_replace("$\\varepsilon$", "EPSILON", $line);
			$line = str_replace("$\\epsilon$", "EPSILON", $line);
			$line = str_replace("$\\eps$", "EPSILON", $line);
			
			//Tokens
			$line = str_replace(" bool", " 'bool'", $line);
			$line = str_replace("double", "'double'", $line);
			$line = str_replace("int", "'int'", $line);
			$line = str_replace("char", "'char'", $line);
			$line = str_replace("| string", "|  'string'", $line);
			$line = str_replace("container", "'container'", $line);
			$line = str_replace("[ ]", "'[ ]'", $line);
			$line = str_replace(" function", " 'function'", $line);
			$line = str_replace("return", "'return'", $line);
			$line = str_replace("using", "'using'", $line);
			$line = str_replace("begin", "'begin'", $line);
			
			//$line = str_replace("end", "'end'", $line);
			
			$line = str_replace("nothing", "'nothing'", $line);
			$line = str_replace("default", "'default'", $line);
			$line = str_replace("break;", "'break';", $line);
			$line = str_replace("break ';'", "'break' ';'", $line);
			$line = str_replace("true", "'true'", $line);
			$line = str_replace("false", "'false'", $line);
			$line = str_replace(" from ", " 'from' ", $line);
			$line = str_replace(" to ", " 'to' ", $line);
			$line = str_replace(" step ", " 'step' ", $line);
			$line = str_replace("else", "'else'", $line);
			$line = str_replace(" OR ", " 'OR' ", $line);
			$line = str_replace(" AND ", " 'AND' ", $line);
			$line = str_replace("LOW", "'LOW'", $line);
			$line = str_replace("HIGH", "'HIGH'", $line);

			
			$check = 0;
			
			for($i = 0; $i <= strlen($line); $i++)
			{
				if($line[$i] == "<" || $line[$i] == ">")
				{
					if($line[$i-1] != "\"")
					{
						if($line[$i-1] != "-" && ($line[$i-1] != "'" && $line[$i+1] != "'"))
						{
							$line[$i] = "";
						}
						else if($line[$i-2] == ".")
						{
							$line[$i] = "";
						}
						else
						{
							if($line[$i] == ">")
							{
								//We have now reached the arrow
								$check = 1;
							}
						}
					}
				}
				
				if($check != 1 && $line[$i] == "'" && strstr($line, '->'))
				{
					$line[$i] = "";
				}
				
				if($line[$i] == "[" && $line[$i+2] == "n")
				{
					$line = str_replace("[", " '[' ", $line);
				}
				if($line[$i] == "]" && $line[$i-2] == "s")
				{
					$line = str_replace("]", " ']' ", $line);
				}
			}		
			
			$line = str_replace("| -", "| '-'", $line);
			$line = str_replace("''!' ='", "'!='", $line);
			//Fix
			$exist = strpos($line, "''");
			

			$line = str_replace("plusminus", "plusminus ", $line);
			$line = str_replace("-> while", "-> 'while' ", $line);

			
			$exist = strpos($line, "endcase");
			$exist2 = strpos($line, "endif");
			$exist3 = strpos($line, "dclend");
			$exist4 = strpos($line, "idend");
			$exist5 = strpos($line, "functionend");
			$exist6 = strpos($line, "subparamsend");
			$exist7 = strpos($line, "subcallexprend");
			$exist8 = strpos($line, "breakend");
			$exist9 = strpos($line, "exprend");
			$exist10 = strpos($line, "termend");
			$exist11 = strpos($line, "numericend");
			$exist12 = strpos($line, "logexprend");
			$exist13 = strpos($line, "andcompend");
			$exist14 = strpos($line, "compend");
			if($exist == false && $exist2 == false && $exist3 == false
			&& $exist4 == false && $exist5 == false && $exist6 == false && $exist7 == false
			&& $exist8 == false && $exist9 == false && $exist10 == false && $exist11 == false && $exist12 == false && $exist13 == false && $exist14 == false)
			{
				$line = str_replace("end", "'end'", $line);
			}
			
			$line = str_replace("-> if", "-> 'if'", $line);
			$line = str_replace("<'else'>", "else", $line);
			
			if($line[0] == "<")
			{
				$line[0] = "";
			}
			
			$line = str_replace("''<stringmidt>''", "'\"'stringmidt'\"'", $line);
			$line= str_replace("expr>", "expr", $line);
			echo htmlentities($line);
			echo "<br />";
		}
		echo "</div>";
		include_once "footer.php";
	}
	else if($_POST["bnf_output"] == "one")
	{
		$input_bnf = $_POST["bnf_input"];
		$array_lines = explode("\n", $input_bnf);
		include_once "header.php";
		?>
			<div id="selectable" onclick="selectText('selectable')">
		<?php
		echo "grammar SPLAT;\n";
		echo "<br />";
		$i=0;
		
		foreach($array_lines as $line)
		{
			
			$line = str_replace("->", ":", $line);
			if(strlen($line) > 1)
			{
				if($i < count($array_lines))
				{
					$next_line = $array_lines[$i+1];
					if(strlen($next_line) < 2)
					{
						$line = "$line;";
					}
				}
			}
			$contains = strstr($line, "a | ... | z | A | ... | Z");
			
			if($contains != false)
			{	
				$l = "";
				$l .= "'" . chr(97) . "'";
				for($j=98; $j <= 122; $j++)
				{
					$l .= "|'";
					$l .= chr($j);
					$l .= "'";
				}
				
				for($j=65; $j <= 90; $j++)
				{
					$l .= "|'";
					$l .= chr($j);
					$l .= "'";
				}
				$line = str_replace("a | ... | z | A | ... | Z", "$l", $line);
			}
			
			$contains = strstr($line, "0 | ... | 9");
			
			if($contains != false)
			{	
				$l = "";
				$l .= "'0'";
				for($j=1; $j <= 9; $j++)
				{
					$l .= "|'";
					$l .= "$j";
					$l .= "'";
				}

				$line = str_replace("0 | ... | 9", "$l", $line);
			}
			
			$contains = strstr($line, "1 | ... | 9");
			
			if($contains != false)
			{	
				$l = "";
				for($j=1; $j <= 9; $j++)
				{
					if($j!=1)
					{
						$l .= "|'";
					}
					else
					{
						$l.="'";
					}
					$l .= "$j";
					$l .= "'";
				}

				$line = str_replace("1 | ... | 9", "$l", $line);
			}
			
			
			echo htmlentities($line);
			echo "<br />";
			$i++;
		}
		echo "</div>";
	}
	else
	{
	
		$input_bnf = $_POST["bnf_input"];
		
		
		$input_bnf = str_replace("\"<--\"", "'ARR'", $input_bnf);
		$input_bnf = str_replace("'<'", "LESSTHAN", $input_bnf);
		$input_bnf = str_replace("'>'", "GREATERTHAN", $input_bnf);
		$input_bnf = str_replace("endcase", "ENDCASEPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace("factor", "FPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace("><", "> <", $input_bnf);

		
		$input_bnf = str_replace(";", " ';' ", $input_bnf);
		$input_bnf = str_replace(")", " ')' ", $input_bnf);
		$input_bnf = str_replace("(", " '(' ", $input_bnf);
		$input_bnf = str_replace("[ ]", " '[ ]' ", $input_bnf);
		$input_bnf = str_replace(",", " ',' ", $input_bnf);
		$input_bnf = str_replace("!=", "NOTEQUAL", $input_bnf);
		$input_bnf = str_replace("!", " '!' ", $input_bnf);
		$input_bnf = str_replace("=", " '=' ", $input_bnf);
		$input_bnf = str_replace("+", " '+' ", $input_bnf);

		$input_bnf = str_replace("*", " '*' ", $input_bnf);
		$input_bnf = str_replace("/", " '/' ", $input_bnf);



		$input_bnf = str_replace("<", "", $input_bnf);
		$input_bnf = str_replace(">", "", $input_bnf);
		$input_bnf = str_replace("$\\rightarrow$", "ARPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace("$\\ra$", "ARPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace(": [", "= [", $input_bnf);
		
		$input_bnf = str_replace("[a - zA - Z]", "LETTERPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace("[0 - 9]", "DIGITPLACEHOLDER", $input_bnf);
		$input_bnf = str_replace("'>='", "GREATERTHANOREQUAL", $input_bnf);
		$input_bnf = str_replace("'<='", "LESSTHANOREQUAL", $input_bnf);
		$input_bnf = str_replace("-", " '-' ", $input_bnf);
		
		//Remove epsilons
		$input_bnf = str_replace("$\\varepsilon$", "EPSILON", $input_bnf);
		$input_bnf = str_replace("$\\epsilon$", "EPSILON", $input_bnf);
		$input_bnf = str_replace("$\\eps$", "EPSILON", $input_bnf);
		$input_bnf = htmlspecialchars($input_bnf);
		
		$input_bnf = str_replace("\n", "<br />", $input_bnf);
		$input_bnf = str_replace("\alt", "|", $input_bnf);

		$input_bnf = str_replace("GREATERTHANOREQUAL", "'>='", $input_bnf);
		$input_bnf = str_replace("LESSTHANOREQUAL", "'<='", $input_bnf);	
		$input_bnf = str_replace("GREATERTHAN", "'>'", $input_bnf);

		$input_bnf = str_replace("LESSTHAN", "'<'", $input_bnf);
		$input_bnf = str_replace("ARROW", "'<--'", $input_bnf);
		$input_bnf = str_replace("NOTEQUAL", "'!='", $input_bnf);
		$input_bnf = str_replace("LETTERPLACEHOLDER", "[a-zA-Z]", $input_bnf);
		$input_bnf = str_replace("DIGITPLACEHOLDER", "[0-9]", $input_bnf);
		
		
		//RESERVED
		$input_bnf = str_replace("bool", "'bool'", $input_bnf);
		$input_bnf = str_replace("int", "'int'", $input_bnf);
		$input_bnf = str_replace("double", "'double'", $input_bnf);
		$input_bnf = str_replace("char", "'char'", $input_bnf);
		$input_bnf = str_replace("string", "'string'", $input_bnf);
		$input_bnf = str_replace(" OR ", " 'OR' ", $input_bnf);
		$input_bnf = str_replace(" AND ", " 'AND' ", $input_bnf);
		$input_bnf = str_replace("true", "'true'", $input_bnf);
		$input_bnf = str_replace("false", "'false'", $input_bnf);
		$input_bnf = str_replace("begin", "'begin'", $input_bnf);
		$input_bnf = str_replace("end", "'end'", $input_bnf);
		$input_bnf = str_replace("if", "'if'", $input_bnf);
		$input_bnf = str_replace("else", "'else'", $input_bnf);
		$input_bnf = str_replace("function ", "'function' ", $input_bnf);
		
		$input_bnf = str_replace("returns", "'returns'", $input_bnf);
		$input_bnf = str_replace("return ", "'return' ", $input_bnf);
		$input_bnf = str_replace("nothing", "'nothing'", $input_bnf);
		$input_bnf = str_replace("break", "'break'", $input_bnf);
		$input_bnf = str_replace("default:", "'default:'", $input_bnf);
		$input_bnf = str_replace("from", "'from'", $input_bnf);
		$input_bnf = str_replace(" to ", " 'to' ", $input_bnf);
		$input_bnf = str_replace("step", "'step'", $input_bnf);
		$input_bnf = str_replace("while", "'while'", $input_bnf);
		$input_bnf = str_replace("ENDCASEPLACEHOLDER", "endcase", $input_bnf);
		$input_bnf = str_replace("FPLACEHOLDER", "factor", $input_bnf);
		$input_bnf = str_replace("using", "'using'", $input_bnf);
		$input_bnf = str_replace(" case ", " 'case' ", $input_bnf);
		
	
		
		//SYMBOLS
		
		$input_bnf = str_replace("| \%", "| '%'", $input_bnf);
		$input_bnf = str_replace("| \^", "| '^'", $input_bnf);
		$input_bnf = str_replace("| \&", "| '&'", $input_bnf);
		$input_bnf = str_replace("amp;", "", $input_bnf); 
		$input_bnf = str_replace("| \_", "| '_'", $input_bnf);
		$input_bnf = str_replace("| |", "| '|'", $input_bnf);
		$input_bnf = str_replace("| \~", "| '~'", $input_bnf);
		$input_bnf = str_replace("| \`", "| '`'", $input_bnf);
		$input_bnf = str_replace("| \{", "| '{'", $input_bnf);
		$input_bnf = str_replace("| \}", "| '}'", $input_bnf);
		$input_bnf = str_replace("| ]", "| ']'", $input_bnf);
		$input_bnf = str_replace("| [", "| '['", $input_bnf);
		$input_bnf = str_replace("| :", "| ':'", $input_bnf);
		$input_bnf = str_replace("| ?", "| '?'", $input_bnf);
		$input_bnf = str_replace("| .", "| '.'", $input_bnf);
		$input_bnf = str_replace("| ' '", "| ' '", $input_bnf);
		
		$input_bnf = str_replace("'string'  ->", "string ->", $input_bnf);
		$input_bnf = str_replace("symbols 'string'", "symbols string", $input_bnf);
		$input_bnf = str_replace("digit 'string'", "digit string", $input_bnf);
		$input_bnf = str_replace("letter 'string'", "letter string", $input_bnf);
	

		//FIX
		$input_bnf = str_replace("ARPLACEHOLDER", " ->", $input_bnf);
		$input_bnf = str_replace("-> 'function'", "-> function", $input_bnf);
		$input_bnf = str_replace("'function'  ->", "function ->", $input_bnf);
		$input_bnf = str_replace("'string'  ->", "string ->", $input_bnf);
		$input_bnf = str_replace("'from'  ->", "from ->", $input_bnf);
		$input_bnf = str_replace("'switch'  ->", "switch ->", $input_bnf);
		$input_bnf = str_replace("-> switch", "-> 'switch'", $input_bnf);
		$input_bnf = str_replace("'while'  ->", "while ->", $input_bnf);
		$input_bnf = str_replace("'if'  ->", "if ->", $input_bnf);
		$input_bnf = str_replace("| 'if'", "| if", $input_bnf);
		$input_bnf = str_replace("| 'while'", "| while", $input_bnf);
		$input_bnf = str_replace("| 'from'", "| from", $input_bnf);
		$input_bnf = str_replace("'end'if ->", "endif ->", $input_bnf);
		$input_bnf = str_replace("'end''if", "endif", $input_bnf);
		$input_bnf = str_replace("'else' 'if'", "'else' if", $input_bnf);
		$input_bnf = str_replace("'bool'eanoperand  ->", "booleanoperand ->", $input_bnf);
		$input_bnf = str_replace("'bool'ean", "boolean", $input_bnf);
		$input_bnf = str_replace("ARR", "<--", $input_bnf);

		$input_bnf = str_replace("'''''", "'\"'", $input_bnf);
		$input_bnf = str_replace("'''", "'\"'", $input_bnf);
		$input_bnf = str_replace("'\"'.", "'.", $input_bnf);
		$input_bnf = str_replace("-> call", "-> 'call'", $input_bnf);
	
		include_once "header.php";
		echo "$input_bnf";
		include_once "footer.php";
	}
	?>