package hu.bme.mit.train.sensor;

import hu.bme.mit.train.controller.TrainControllerImpl;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.user.TrainUserImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainController controller;

    TrainUser user;

    TrainSensor sensor;

    TrainSensor sensor2;

    TrainUser mockedUser = Mockito.mock(TrainUser.class);
    TrainController mockedController = Mockito.mock(TrainController.class);

    @Before
    public void before() {
        controller = new TrainControllerImpl();
        sensor = new TrainSensorImpl(controller, new TrainUserImpl(controller));
        sensor2 = new TrainSensorImpl(mockedController, mockedUser);
        sensor.overrideSpeedLimit(50);
    }

    @Test
    public void ThisIsAnExampleTestStub() {
        sensor.saveCurrentState();
        Assert.assertFalse(sensor.getTachograf().isEmpty());
        Assert.assertTrue(sensor.getTachograf().containsValue(0));
        Assert.assertFalse(sensor.getTachograf().containsValue(1));
    }

    @Test
    public void TestAlarm() {
        when(mockedController.getReferenceSpeed()).thenReturn(100);
        sensor2.alarm();
        verify(mockedUser, times(1)).setAlarmState(true);
    }

    @Test
    public void TestAlarm2() {
        when(mockedController.getReferenceSpeed()).thenReturn(1);
        sensor2.overrideSpeedLimit(50);
        sensor2.alarm();
        verify(mockedUser, times(0)).setAlarmState(true);
    }

    @Test
    public void TestAlarm3() {
        when(mockedController.getReferenceSpeed()).thenReturn(1);
        sensor2.overrideSpeedLimit(501);
        sensor2.alarm();
        verify(mockedUser, times(1)).setAlarmState(true);
    }

    @Test
    public void TestAlarm4() {
        when(mockedController.getReferenceSpeed()).thenReturn(1);
        sensor2.overrideSpeedLimit(-50);
        sensor2.getSpeedLimit();
        sensor2.alarm();
        verify(mockedUser, times(1)).setAlarmState(true);
    }

}
