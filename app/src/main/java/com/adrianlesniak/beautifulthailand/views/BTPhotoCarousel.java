package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.Photo;
import com.adrianlesniak.beautifulthailand.utilities.MapsApiHelper;

/**
 * Created by adrian on 04/02/2017.
 */

public class BTPhotoCarousel extends ViewPager {

    public BTPhotoCarousel(Context context) {
        this(context, null);
    }

    public BTPhotoCarousel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPhotos(FragmentManager fragmentManager, Photo[] photos) {
        PhotoCarouselAdapter adapter = new PhotoCarouselAdapter(fragmentManager, photos);
        this.setAdapter(adapter);
    }

    public static class PhotoCarouselAdapter extends FragmentStatePagerAdapter {

        private Photo[] mPhotos;

        public PhotoCarouselAdapter(FragmentManager fm, Photo[] photos) {
            super(fm);

            this.mPhotos = photos;
        }


        @Override
        public int getCount() {
            return this.mPhotos != null? this.mPhotos.length : 0;
        }

        @Override
        public Fragment getItem(int position) {
            return PhotoFragment.getInstance(this.mPhotos[position]);
        }

    }

    public static class PhotoFragment extends Fragment {

        public static final String BUNDLE_PHOTO_REFERENCE = "photo_reference";

        public static final String BUNDLE_PHOTO_WIDTH = "photo_width";

        public static Fragment getInstance(Photo photo) {

            PhotoFragment f = new PhotoFragment();

            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_PHOTO_REFERENCE, photo.photo_reference);
            bundle.putInt(BUNDLE_PHOTO_WIDTH, photo.width);

            f.setArguments(bundle);

            return f;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_photo, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            String photoReference = getArguments().getString(BUNDLE_PHOTO_REFERENCE);

            int maxWidth = getArguments().getInt(BUNDLE_PHOTO_WIDTH);


            ImageView imageView = ((ImageView) view.findViewById(R.id.photo_image_view));

            MapsApiHelper.getInstance(getContext())
                    .loadPhoto(photoReference, maxWidth, imageView);

        }
    }
}
