var coaHoverBackgroundColor = '#fdeb9f';

function mouseOver(object)
{
	if(object == null || object.tagName == null)
		return;

	if(object.tagName.toLowerCase() == 'li')
	{
		object.style.cursor = 'pointer';
		object.style.backgroundColor = coaHoverBackgroundColor;
	}
	else if(object.tagName.toLowerCase() == 'input' || object.tagName.toLowerCase() == 'button')
	{
		if(object.type == 'button' || object.type == 'submit')
		{
			object.className = 'coaButtonHov'
		}
	}
	else if(object.tagName.toLowerCase() == 'img')
	{
		if(object.className == 'coaAction')
			object.className = 'coaActionHov'
	}
	else if(object.tagName.toLowerCase() == 'tr')
	{
		if(object.className == 'coaOdd')
		{
			object.style.backgroundColor = coaHoverBackgroundColor;
		}
		if(object.className == 'coaEven')
		{
			object.style.backgroundColor = coaHoverBackgroundColor;
		}
	}
}

function mouseOut(object)
{
	if(object == null || object.tagName == null)
		return;

	if(object.tagName.toLowerCase() == 'li')
	{
		object.style.cursor = 'none';
		object.style.backgroundColor = '#F9F7FB';
	}
	else if(object.tagName.toLowerCase() == 'input' || object.tagName.toLowerCase() == 'button')
	{
		if(object.type == 'button' || object.type == 'submit')
		{
			object.className = 'coaButton'
		}
	}
	else if(object.tagName.toLowerCase() == 'img')
	{
		if(object.className == 'coaActionHov')
			object.className = 'coaAction'
	}
	else if(object.tagName.toLowerCase() == 'tr')
	{
		if(object.className == 'coaOdd')
		{
			object.style.backgroundColor = '#f3f5f5';
		}
		if(object.className == 'coaEven')
		{
			object.style.backgroundColor = '#e9ebeb';
		}
	}
}