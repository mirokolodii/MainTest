package ua.mainacad.maintest.maintest.ui.photos;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.mainacad.maintest.maintest.MyApp;
import ua.mainacad.maintest.maintest.model.Photo;

import java.util.ArrayList;
import java.util.List;


@InjectViewState
public class PhotosListPresenter extends MvpPresenter<IPhotoListView> {
    private ArrayList<Photo> mPhotos = new ArrayList<>();

    PhotosListPresenter() {
        Single<List<Photo>> photos = MyApp.get().getApi().getAllPhotos();
        photos
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                photos1 -> {
                    if (photos1 != null && !photos1.isEmpty()) {
                        mPhotos.clear();
                        mPhotos.addAll(photos1);
                        getViewState().setPhotos(mPhotos);
                    }
                },
                Throwable::printStackTrace);
    }

    @Override
    public void attachView(IPhotoListView view) {
        super.attachView(view);
        getViewState().setPhotos(mPhotos);
    }
}
