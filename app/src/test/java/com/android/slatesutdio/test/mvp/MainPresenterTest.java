package com.android.slatesutdio.test.mvp;

import com.android.slatestudio.test.data.model.GeofenceModel;
import com.android.slatestudio.test.ui.main.MainContracts;
import com.android.slatestudio.test.ui.main.MainPresenter;
import com.android.slatesutdio.test.testCase.AppRobolectricTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MainPresenterTest extends AppRobolectricTestCase {

    @Inject
    MainPresenter presenter;
    @Mock
    MainContracts.View view;

    @Before
    public void setUp() throws Exception {
        component().inject(this);
        super.setUp();
        presenter = spy(presenter);
    }

    @Test
    public void onViewAttachTest_withNoGeofences_shouldNotShowGeofence() {
        //Given
        presenter.onAttachView(view);
        when(preferenceHelper.getGeofences()).thenReturn(new ArrayList<>());
        //when
        presenter.onAttachView(view);
        //Verify
        verify(geofenceRepository, never()).trackGeofenceTransition(any());
    }

//    @Test
//    public void onViewAttachTest_withGeofences_shouldTrackGeofence() {
//        //Given
//        presenter.onAttachView(view);
//        ArrayList<GeofenceModel> geofenceModels = new ArrayList<>();
//        geofenceModels.add(new GeofenceModel("", 3.D, 3.D, 3.D));
//        when(geofenceRepository.getGeofences()).thenReturn(geofenceModels);
//        when(preferenceHelper.getGeofences()).thenReturn(geofenceModels);
//        //when
//        presenter.onAttachView(view);
//        //Verify
//        verify(geofenceRepository).trackGeofenceTransition(any());
//    }

    @Test
    public void onStart_withNoGeofences_shouldNotShowGeofence() {
        //Given
        presenter.onAttachView(view);
        when(preferenceHelper.getGeofences()).thenReturn(new ArrayList<>());
        //when
        presenter.onStart();
        //Verify
        verify(view, never()).showGeofence(any());
    }

    @Test
    public void onStart_withGeofences_shouldShowGeofence() {
        //Given
        presenter.onAttachView(view);
        ArrayList<GeofenceModel> geofenceModels = new ArrayList<>();
        geofenceModels.add(new GeofenceModel("", 3.D, 3.D, 3.D));
        when(preferenceHelper.getGeofences()).thenReturn(geofenceModels);
        when(geofenceRepository.getGeofences()).thenReturn(geofenceModels);
        // when
        presenter.onStart();
        //Verify
        verify(view).showGeofence(any());
    }

    @Test
    public void onAddGeofenceClick_shouldOpenCreateGeofenceScreen() {
        //Given
        presenter.onAttachView(view);
        // when
        presenter.onAddGeofenceClick();
        //Verify
        verify(view).openCreateGeofenceScreen();
    }

    @Test
    public void onMapReady_shouldShowUserCurrentLocation() {
        //Given
        presenter.onAttachView(view);
        // when
        presenter.onMapReady();
        //Verify
        verify(view).showUserCurrentLocation();
    }

    @Test
    public void onDeleteGeofence_shouldRemoveGeofenceAndClearMap() {
        //Given
        presenter.onAttachView(view);
        String id = UUID.randomUUID().toString();
        GeofenceModel geofenceModel = new GeofenceModel(id, 4.3, 3.3, 1000.9);
        geofenceRepository.addGeofence(geofenceModel);
        // when
        presenter.onDeleteGeofenceClicked(id);
        //Verify
        verify(preferenceHelper).removeGeofence(geofenceModel);
        verify(view).clearMapObjects();
    }


    @After
    public void unsubAll() {
        presenter.onDestroyView();
    }
}