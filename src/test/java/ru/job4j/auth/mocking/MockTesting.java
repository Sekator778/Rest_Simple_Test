package ru.job4j.auth.mocking;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

/**
 *
 */

public class MockTesting {
    /**
     * пример демонстрирует создание мок-итератора и «заставляет» его возвращать
     * «Hello» при первом вызове метода next().
     */
    @Test
    public void iterator_will_return_hello_world() {
        //подготавливаем
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello").thenReturn("World").thenReturn(" !");
        //выполняем
        String result = i.next() + " " + i.next() + i.next();
        //сравниваем
        assertEquals("Hello World !", result);
    }

    /**
     * Заглушки также могут возвращать различные значения в зависимости от передаваемых в метод аргументов
     *
     * Здесь мы создаём объект-заглушку Comparable, и возвращаем 1 в случае, если он сравнивается
     * с определённым String-значением («Test», в данном случае).
     */
    @Test
    public void with_arguments() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo("Test")).thenReturn(1);
        assertEquals(1, c.compareTo("Test"));
    }

    /**
     * Если метод имеет какие-то аргументы, но Вам всё равно, что будет в них передано
     * или предсказать это невозможно, то используйте anyInt()
     * (и альтернативные значения для других типов).
     */
    @Test
    public void with_unspecified_arguments() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        assertEquals(-1, c.compareTo(534343434));
    }


}
