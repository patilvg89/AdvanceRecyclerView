# AdvanceRecyclerView

* Add it in your root build.gradle at the end of repositories:
  allprojects {
	  repositories {
		  ...
		  maven { url 'https://jitpack.io' }
	  }
  }
  
* Add the dependency to app build.gradle
	dependencies {
	   compile 'com.github.patilvg89:AdvanceRecyclerView:1.0.0'
	}

1) Add below layout to your xml
  <com.virendra.view.RecyclerViewEmptySupport
   android:id="@+id/recyclerview1"
   android:layout_width="match_parent"
   android:layout_height="match_parent" />

2) Create Adapter and set Adapter to recyclerview1

3) Use setAdapter() method as per your requirement
   a)  recyclerview1.setAdapter(adapter);
   b)  recyclerview1.setAdapterWithEmptyTextView(adapter, "There are no record to show...");
   c)  recyclerview1.setAdapterWithEmptyImageView(adapter, ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
   d)  recyclerview1.setAdapterWithEmptyImageView(adapter, ContextCompat.getDrawable(this, R.mipmap.ic_launcher), 100, 100); // unit is dp
   e)  recyclerview1.setAdapterWithEmptyImageView(adapter, null); //To show library default image

4) Empty Text and Image can be added in xml layout
   If your mentioned text and image in xml layout then ImageView has more priority than TextView
   a)  For empty text:  import at parent layout:  xmlns:app="http://schemas.android.com/apk/res-auto"
 
       <com.virendra.view.RecyclerViewEmptySupport
         android:id="@+id/recyclerview1"
         android:layout_width="match_parent"
         android:layout_height="match_parent"
         app:empty_list_text="No data found..." />
 
    b) For image : import at parent layout:  xmlns:app="http://schemas.android.com/apk/res-auto"
       If height and width not mentioned then image parameters are WRAP_CONTENT
 
       <com.virendra.view.RecyclerViewEmptySupport
        android:id="@+id/recyclerview1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:empty_image_drawable="@mipmap/ic_launcher"
        app:empty_image_height="100dp"
        app:empty_image_width="100dp" />

5) Recyclerview Pagination (Currently set Empty image/text not supported)
     int visibleThreshold = 10; // visible items
     int totalItemCount = 101;//value from  server response where server returns the total count for list
     
     recyclerview.setPaginationAdapter(adapter1, visibleThreshold, totalItemCount, new RecyclerViewCallback() {
      @Override
      public void loadMoreItems(int pageNo) {
         //do API call and get response
         API_CALL(pageNo);
         
         //add response to list
         list.addAll(...);
         
         //update adapter
         recyclerviewSupport.updatePaginationAdapter(adapter1, list.size());
       }

       @Override
       public void hasLoadedAllItems(boolean value) {
           Log.d("TAG", "Reached to end");
       }
   }); 
