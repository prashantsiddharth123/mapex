# Introduction #

I bet my horses that this might be the most used class of this project, so I'll give a brief explanation on how to use it.

The idea of this heatmap, is that a semi transparant canvas will be added on top of your mapview. The heatmap overlay will do everything for you, all you need to provide it is a set of HeatPoints to it.

Please also note, that refreshing the heatmap's points is also your responsibility, by default it will clear the canvas once the user clicks the screen otherwise you would get a fixed image on top of your map.

So here's  a very, very simple way of using your heatmap:

```

this.helper = new MapExDBHelper(this);
setContentView(R.layout.heat);
final SimpleMapView mapview = (SimpleMapView)findViewById(R.id.mapview);
this.overlay = new HeatMapOverlay(20000, mapview);
mapview.getOverlays().add(overlay);
mapview.addPanChangeListener(new PanChangeListener() {

@Override
public void onPan(GeoPoint old, GeoPoint current) {
List<HeatPoint> points = helper.loadPoints(mapview.getBounds());
if(points.size() > 0){
overlay.update(points);
}

}
});

```

The heatmap only requires a mapview instance (does not need to be our simplemapview enhanced version ;) ) and the radius in meters of each point. Try a few variants to see which one would be the best for your.

So, whenever you want to refresh your heatmap, you should call update(List

&lt;HeatPoint&gt;

 points). In this example it's done on the pan of the map, but you could trigger this anyway you desire.

That's pretty much it. Enjoy

