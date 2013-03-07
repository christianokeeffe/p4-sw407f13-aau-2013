<?php
	include_once "header.php";
?>
	<h2>BNF-Converter</h2>
	<form action="bnf_take_convert.php" enctype="multipart/form-data" method="post">
		<dl>
			Vælg output:
			<br />

				<label class="radio">
				kfG-edit: <input type="radio" name="bnf_output" value="three" />
				</label>

				<label class="radio">
				Brics: <input type="radio" name="bnf_output" value="two" />
				</label>
				
				<label class="radio">
				kfG-edit til ANTLR: <input type="radio" name="bnf_output" value="one" />
				</label>

		
			<dt>Input BNF:</dt>
			<dd>
				<textarea name="bnf_input" style="min-height:600px; min-width:600px;"></textarea>
			</dd>
					 
			<dd><input class="btn btn-primary" type="submit" value="Convert Me" /></dd>
		</dl>
	</form>
<?php
	include_once "footer.php";
?>