package com.android.slatesutdio.test.mvp;

import com.android.slatestudio.test.ui.main.MainContracts;
import com.android.slatestudio.test.ui.main.MainPresenter;
import com.android.slatesutdio.test.testCase.AppRobolectricTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.inject.Inject;

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
    public void onViewAttachTest_no_geofences() {
        //Given
        presenter.onAttachView(view);
        //Verify
        verify(view, never()).showGeofence(any());
    }

    @After
    public void unsubAll() {
        presenter.onDestroyView();
    }

}