/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
/*
   Client side JavaScript object for tokenization of a string.
   Best used for something as simple as a comma separated record of values.

   Sample usage:

   <script type="text/javascript" language="javascript" src="../lib/stringTokenizer.js"></script>
   <script type="text/javascript" language="javascript">

   	var separator = ",";
   	var names = "one,two,three";

   	var tokenizer = new StringTokenizer (names, separator);

   	while (tokenizer.hasMoreTokens())
   	{
   		document.write("<p>Name " + tokenizer.nextToken() + "</p>");
   	}  // end while

   </script>

*/


/*
   Go through material, putting each token into a new array.
   Return      - the array with all the tokens in it.
*/
function getTokens()
{
   // Create array of tokens.
   tokens = new Array();

   // If no separators found, single token is the material string itself.
	if (this.material.indexOf (separator) < 0)
	{
		tokens [0] = this.material;
		return tokens;
	}  // end if

   // Establish initial start and end positions of the first token.
   start = 0;
   end = this.material.indexOf (separator, start);

   // Counter for how many tokens were found.
   var counter = 0;

   // Go through material, token at a time.
 	while (this.material.length - start > 1)
	{
		nextToken = this.material.substring (start, end);
		start = end + this.sep_size;
		if (this.material.indexOf (separator, start + this.sep_size) < 0)
		{
			end = this.material.length;
		}  // end if
		else
		{
			end = this.material.indexOf (separator, start + this.sep_size);
		}  // end else

      tokens [counter] = nextToken;

		counter ++;
	}   // end if

   // Return the initialised array.
   return tokens;


}  // end getTokens function


/*
   Return a count of the number of tokens in the material.
   Return      - int number of tokens in material.
*/
function count_Tokens()
{
  return this.tokens.length;
}  // end countTokens function

/*
   Get next token in material.
   Return      - next token in material.
*/
function next_Token()
{

   if (this.tokensReturned >= this.tokens.length)
   {
      return null;
   }  // end if
   else
   {
      return this.tokens [this.tokensReturned ++];
   }  // end else


}  // end nextToken function

/*
   Tests if there are more tokens available from this tokenizer's string. If
   this method returns true, then a subsequent call to nextToken
   will successfully return a token.

   Return      true if more tokens, false otherwise.
*/
function hasMore_Tokens()
{
   if (this.tokensReturned < this.tokens.length)
   {
      return true;
   }  // end if
   else
   {
      return false;
   }  // end else
}  // end hasMoreTokens function

/*
   Constructor.
   Split up a material string based upong the separator.

   Param    -  material, the String to be split up.
   Param    -  separator, the String to look for within material. Should be
               something like "," or ".", not a regular expression.

*/
function StringTokenizer (material, separator)
{
   // Attributes.
   this.material = material;
   this.separator = separator;
   this.sep_size = separator.length;

   // Operations.
   this.getTokens = getTokens;
   this.countTokens = count_Tokens;
   this.hasMoreTokens = hasMore_Tokens;
   this.nextToken = next_Token;

   // Initialisation code.
   this.tokens = this.getTokens();
   this.tokensReturned = 0;

}  // end constructor

