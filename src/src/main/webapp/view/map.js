var drawingManager;
var selectedShape;
var map;
var obj;
var colors = ['#1E90FF', '#FF1493', '#32CD32', '#FF8C00', '#4B0082'];
var selectedColor;
var colorButtons = {};

function clearSelection() {
  if (selectedShape) {
    selectedShape.setEditable(false);
    selectedShape = null;
    document.getElementById("area").innerHTML =0;
  }
}

function setSelection(shape) {
  clearSelection();
  selectedShape = shape;
  shape.setEditable(true);
  selectColor(shape.get('fillColor') || shape.get('strokeColor'));
  google.maps.event.addListener(shape.getPath(), 'set_at', calcar);
  google.maps.event.addListener(shape.getPath(), 'insert_at', calcar);
}

//the location of the center of polygon(polyline) 
function codeAddress(){
	var path = selectedShape.getPath();
	var bounds = new google.maps.LatLngBounds();
	for(var i=0;i<path.length;i++){
		var point = new google.maps.LatLng(path.getAt(i).lat(),path.getAt(i).lng());
		bounds.extend(point);
	}
	var latlng = bounds.getCenter();
	//var latlng = bounds.toSpan();
	//alert(latlng.lat());
	var geocoder = new google.maps.Geocoder;
	geocoder.geocode({'location': latlng}, function(results, status) {
        if (status === 'OK') {
          if (results[0]) {
        	  document.getElementById("address").innerHTML = "address =" + results[0].formatted_address;
          } else {
        	  document.getElementById("address").innerHTML = "address = No results found";
          }
        } else {
          document.getElementById("address").innerHTML = "address = Geocoder failed due to:"+status;
        }
      });
}

function showClu( obj){
	var wordsarea = document.getElementById("clusterwords");
	var childsarea = wordsarea.childNodes;
	 for(var i = childsarea.length - 1; i >= 0; i--) { 
		 wordsarea.removeChild(childsarea[i]); 
		}
	var pp = document.createElement("p");
	pp.innerText=obj.list;
	wordsarea.appendChild(pp);
	 var linkul = document.getElementById("link");
	 var childs = linkul.childNodes;
	 for(var i = childs.length - 1; i >= 0; i--) { 
		  linkul.removeChild(childs[i]); 
		}
	 for(var j = 0; j< obj.txtlist.length;j++){
		 if(obj.txtlist[j].textSen == ""){
			 continue;
		 }
		 var element2 =  document.createElement("a");
			element2.id = "clua";
			element2.innerText = obj.txtlist[j].textSen;
			element2.href =obj.txtlist[j].urlLink;
			element2.target="_blank";
			linkul.appendChild(element2);
			var element3 = document.createElement("p");
			element3.innerText = "key: "+obj.txtlist[j].reKeyWords;
			linkul.appendChild(element3);
			var tt = document.createElement("div");
            tt.innerHTML = "<br/><br/>";
            linkul.appendChild(tt);
	 }
}
function ccccc(x){
	alert(x);
}
//response process:1,get the select by id 2, add option to the select 3, set the click event to every option
function processResponse(ret){
				obj = ret;
				//var obj = JSON.parse(str);
				var nav = document.getElementById("filterby");
				for(var i = 0; i<obj.length; i++){
					var element1 =  document.createElement("option");
					element1.innerText = obj[i].clusterName;
					element1.id = i+1;
					nav.appendChild(element1);
				}
}
function filterEvent(){
	var nav = document.getElementById("filterby");
	var index = nav.options[nav.selectedIndex].id; 
	if (index==0){
		document.getElementById("clusterwords").innerText="null";
		document.getElementById("link").innerText="null";
	}
	else showClu(obj[index]);
}
//send the function of request
 function sendRequest (place,num) {
	var placeName = place;

	 $.ajax({
		 type: "POST",
		 url:   "place.do",
		 cache: false,
		 traditional: "true",
		 async:false, 
		 data:{"placeName":place,"num":num},
		 dataType: "json",
		 success: function (ret) {
			 processResponse(ret);
		 },
		 error: function (ret) {
			 alert(ret);
		 }
		 });
	//XMLHttpReq.open("POST",url,false);
	//XMLHttpReq.setRequestHeader("Content-Type","application/json");
	//XMLHttpReq.onreadystatechange=processResponse;
	//XMLHttpReq.send(data);
	//XMLHttpReq.send();

}

//the location of the place contained in the polygon
function codeAddressfirst(){
	var path = selectedShape.getPath();
	var bounds = new google.maps.LatLngBounds();
	for(var i=0;i<path.length;i++){
		var point = new google.maps.LatLng(path.getAt(i).lat(),path.getAt(i).lng());
		bounds.extend(point);
	}
	var request = {
			bounds:bounds
			
	};
	var service = new google.maps.places.PlacesService(map);
	service.nearbySearch(request, callback);
}
function callback(results, status,pagination) {
	  var placename=new Array(); 
	  var j=0;
	  if (status == google.maps.places.PlacesServiceStatus.OK) {
		  //sendRequest("",false);
	    for (var i = 0; i < results.length; i++) {
	    	    if(results[i].types.includes("country") || results[i].types.includes("locality") )
	    	    	continue;
	    	    if(results[i].name!=null){
	    	    	placename[j++]=results[i].name;

	    		//sendRequest(results[i].name,true);
	    	    }
	    }
	    if(pagination.hasNextPage){
	    		pagination.nextPage();
	    }
	    if(j==0)
	    	alert("No selected place");
	    else sendRequest(placename,j);
	  }
	}




function calcar() {
	var area = google.maps.geometry.spherical.computeArea(selectedShape.getPath());
    document.getElementById("area").innerHTML = area;
   // codeAddress();
    
}

function deleteSelectedShape() {
  if (selectedShape) {
    selectedShape.setMap(null);
  }
}

function selectColor(color) {
  selectedColor = color;
  for (var i = 0; i < colors.length; ++i) {
    var currColor = colors[i];
    colorButtons[currColor].style.border = currColor == color ? '2px solid #789' : '2px solid #fff';
  }

  // Retrieves the current options from the drawing manager and replaces the
  // stroke or fill color as appropriate.
  var polylineOptions = drawingManager.get('polylineOptions');
  polylineOptions.strokeColor = color;
  drawingManager.set('polylineOptions', polylineOptions);

  var rectangleOptions = drawingManager.get('rectangleOptions');
  rectangleOptions.fillColor = color;
  drawingManager.set('rectangleOptions', rectangleOptions);

  var circleOptions = drawingManager.get('circleOptions');
  circleOptions.fillColor = color;
  drawingManager.set('circleOptions', circleOptions);

  var polygonOptions = drawingManager.get('polygonOptions');
  polygonOptions.fillColor = color;
  drawingManager.set('polygonOptions', polygonOptions);
}

function setSelectedShapeColor(color) {
  if (selectedShape) {
    if (selectedShape.type == google.maps.drawing.OverlayType.POLYLINE) {
      selectedShape.set('strokeColor', color);
    } else {
      selectedShape.set('fillColor', color);
    }
  }
}

function makeColorButton(color) {
  var button = document.createElement('span');
  button.className = 'color-button';
  button.style.backgroundColor = color;
  google.maps.event.addDomListener(button, 'click', function() {
    selectColor(color);
    setSelectedShapeColor(color);
  });

  return button;
}

function buildColorPalette() {
  var colorPalette = document.getElementById('color-palette');
  for (var i = 0; i < colors.length; ++i) {
    var currColor = colors[i];
    var colorButton = makeColorButton(currColor);
    colorPalette.appendChild(colorButton);
    colorButtons[currColor] = colorButton;
  }
  selectColor(colors[0]);
}

function createMap(){
var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 10,
    center: new google.maps.LatLng(-34.397, 150.644),
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    disableDefaultUI: true,
    zoomControl: true
  });
var infoWindow = new google.maps.InfoWindow({map:map});
if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };

      infoWindow.setPosition(pos);
      infoWindow.setContent('Location found.');
      map.setCenter(pos);
    }, function() {
      handleLocationError(true, infoWindow, map.getCenter());
    });
  } else {
    // Browser doesn't support Geolocation
    handleLocationError(false, infoWindow, map.getCenter());
  }
return map;
}
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
                          'Error: The Geolocation service failed.' :
                          'Error: Your browser doesn\'t support geolocation.');
  }
function initialize() {
  map = createMap();

  var polyOptions = {
    strokeWeight: 0,
    fillOpacity: 0.45,
    editable: true
  };
  // Creates a drawing manager attached to the map that allows the user to draw
  // markers, lines, and shapes.
  drawingManager = new google.maps.drawing.DrawingManager({
    drawingMode: google.maps.drawing.OverlayType.POLYGON,
    markerOptions: {
        draggable: true
      },
      polylineOptions: {
        editable: true
      },
      rectangleOptions: polyOptions,
      circleOptions: polyOptions,
      polygonOptions: polyOptions,
    map: map
  });

  google.maps.event.addListener(drawingManager, 'overlaycomplete', function(e) {
    if (e.type != google.maps.drawing.OverlayType.MARKER) {
      // Switch back to non-drawing mode after drawing a shape.
      drawingManager.setDrawingMode(null);

      // Add an event listener that selects the newly-drawn shape when the user
      // mouses down on it.
      var newShape = e.overlay;
      newShape.type = e.type;
        setSelection(newShape);
    }
  });

  // Clear the current selection when the drawing mode is changed, or when the
  // map is clicked.
  google.maps.event.addListener(drawingManager, 'drawingmode_changed', clearSelection);
  google.maps.event.addListener(map, 'click', clearSelection);
  google.maps.event.addDomListener(document.getElementById('delete-button'), 'click', deleteSelectedShape);

  buildColorPalette();
}
 

   