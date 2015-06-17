# About #

This project is aimed to provide extensions to the Google Maps component present on the Android API.

The project is to be used as a library project on android projects. Please refer to [Android Reference](http://developer.android.com/guide/developing/projects/projects-eclipse.html) for more information.

The icons provided as sample drawables are from [Dry Icons](http://www.dryicons.com) please refer to their license before using them inside your application.


# Features #

The initial version (0.0.1) has the following features

  * Support for events from extended map view (OnPan and OnZoom)
  * Extended map view can return the lat/lon bounds of the screen
  * Selection Overlay: Allows drawing an area on screen
    * Suports resize
    * Drag and Drop
  * Get internal area
  * Heatmap overlay, draws a heat map on the screen, requires only a dataset of points (runs asynchronously for best performance)
  * LatLon class with several methods for distance, bearing, midpoint using GIS coordinates


# TODO #
Some new features that I'm planning to release soon:

  * Clustering for markers
  * Fix a problem with OnPan on the heatmap

# Screenshots #

<div>
<img src='http://wiki.mapex.googlecode.com/hg/images/selection.png' width='205' height='410' />
<img src='http://wiki.mapex.googlecode.com/hg/images/drag.png' width='205' height='410' />
<img src='http://wiki.mapex.googlecode.com/hg/images/bounds.png' width='205' height='410' />
</div>
<div>
<img src='http://wiki.mapex.googlecode.com/hg/images/heatmap.png' />
</div>

# Contribute #

I'm really trying hard to push this project, please feel free to join. Just contact me. Issues with feature requests are welcome as well.