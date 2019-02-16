package com.android.slatesutdio.test.mvp;

import com.android.slatestudio.test.ui.creategeofence.CreateGeofenceContracts;
import com.android.slatestudio.test.ui.creategeofence.CreateGeofencePresenter;
import com.android.slatesutdio.test.testCase.AppRobolectricTestCase;
import com.google.android.gms.maps.model.LatLng;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateGeofencePresenterTest extends AppRobolectricTestCase {

    @Inject
    CreateGeofencePresenter presenter;
    @Mock
    CreateGeofenceContracts.View view;

    @Before
    public void setUp() throws Exception {
        component().inject(this);
        super.setUp();
        presenter = spy(presenter);
    }

    @Test
    public void onViewAttachTest_shouldShowSelectLocationView() {
        //Given
        presenter.onAttachView(view);
        when(preferenceHelper.getGeofences()).thenReturn(new ArrayList<>());
        //when
        presenter.onAttachView(view);
        //Verify
        verify(view, times(2)).showSelectLocationView();
    }

    @Test
    public void onSelectLocation_shouldUpdateLocationAndShowGeofenceRadius() {
        //Given
        presenter.onAttachView(view);
        double latitude = 3.33d;
        double longitude = 3.33d;
        LatLng latLng = new LatLng(latitude, longitude);
        // when
        presenter.onSelectLocation(latLng);
        //Verify
        assertEquals(presenter.getMGeofenceModel().getLat(), latitude);
        assertEquals(presenter.getMGeofenceModel().getLng(), longitude);
        verify(view).showSelectRadiusView();
    }

    @Test
    public void onUpdateRadius_shouldUpdateGeofenceRadius() {
        //Given
        presenter.onAttachView(view);
        int selectedRadius = 1;
        double radiusInMeters = 2000D;
        // when
        presenter.onUpdateRadius(selectedRadius);
        //Verify
        assertEquals(presenter.getMGeofenceModel().getRadius(), radiusInMeters);
        verify(view).showUpdatedGeofence(any(), anyString());
    }

    @Test
    public void onCreateGeofence_shouldAddGeofence() {
        //Given
        presenter.onAttachView(view);
        //when
        presenter.onCreateGeofenceClick();
        //Verify
//        verify(geofenceRepository).addGeofence(any());
        verify(preferenceHelper).addGeofence(any());
        verify(view).showGeofenceAddedSuccessfully();
    }

    @After
    public void unsubAll() {
        presenter.onDestroyView();
    }
}