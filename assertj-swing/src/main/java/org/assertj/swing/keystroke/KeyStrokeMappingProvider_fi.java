package org.assertj.swing.keystroke;

import org.assertj.core.util.Lists;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

import static java.awt.event.InputEvent.ALT_GRAPH_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_SPACE;
import static org.assertj.swing.keystroke.KeyStrokeMapping.mapping;

/**
 * Mapping between characters and {@code KeyStroke}s for locale {@code Locale.FINNISH}.
 *
 * @author Timo Mikkilä
 */
public class KeyStrokeMappingProvider_fi implements KeyStrokeMappingProvider {
    /**
     * @return the mapping between characters and {@code KeyStroke}s for locale {@code Locale.FINNISH}.
     */
    @Override
    public @Nonnull
    Collection<KeyStrokeMapping> keyStrokeMappings() {
        return KeyStrokeMappingProvider_fi.SingletonHolder.instance;
    }

    // Thread-safe, lazy-loading singleton.
    private static class SingletonHolder {
        static List<KeyStrokeMapping> instance = createMappings();
    }

    private static @Nonnull List<KeyStrokeMapping> createMappings() {
        List<KeyStrokeMapping> mappings = Lists.newArrayList();
        mappings.add(mapping('1', VK_1, NO_MASK));
        mappings.add(mapping('!', VK_1, SHIFT_MASK));
        mappings.add(mapping('2', VK_2, NO_MASK));
        mappings.add(mapping('"', VK_2, SHIFT_MASK));
        mappings.add(mapping('@', VK_2, ALT_GRAPH_MASK));
        mappings.add(mapping('3', VK_3, NO_MASK));
        mappings.add(mapping('#', VK_3, SHIFT_MASK));
        mappings.add(mapping('£', VK_3, ALT_GRAPH_MASK));
        mappings.add(mapping('4', VK_4, NO_MASK));
        mappings.add(mapping('¤', VK_4, SHIFT_MASK));
        mappings.add(mapping('$', VK_4, ALT_GRAPH_MASK));
        mappings.add(mapping('5', VK_5, NO_MASK));
        mappings.add(mapping('%', VK_5, SHIFT_MASK));
        mappings.add(mapping('6', VK_6, NO_MASK));
        mappings.add(mapping('&', VK_6, SHIFT_MASK));
        mappings.add(mapping('‚', VK_6, ALT_GRAPH_MASK));
        mappings.add(mapping('7', VK_7, NO_MASK));
        mappings.add(mapping('/', VK_7, SHIFT_MASK));
        mappings.add(mapping('{', VK_7, ALT_GRAPH_MASK));
        mappings.add(mapping('8', VK_8, NO_MASK));
        mappings.add(mapping('(', VK_8, SHIFT_MASK));
        mappings.add(mapping('[', VK_8, ALT_GRAPH_MASK));
        mappings.add(mapping('9', VK_9, NO_MASK));
        mappings.add(mapping(')', VK_9, SHIFT_MASK));
        mappings.add(mapping(']', VK_9, ALT_GRAPH_MASK));
        mappings.add(mapping('0', VK_0, NO_MASK));
        mappings.add(mapping('=', VK_0, SHIFT_MASK));
        mappings.add(mapping('}', VK_0, ALT_GRAPH_MASK));
        mappings.add(mapping('+', VK_PLUS, NO_MASK));
        mappings.add(mapping('?', VK_PLUS, SHIFT_MASK));
        mappings.add(mapping('\\', VK_PLUS, ALT_GRAPH_MASK));
        mappings.add(mapping('q', VK_Q, NO_MASK));
        mappings.add(mapping('Q', VK_Q, SHIFT_MASK));
        mappings.add(mapping('w', VK_W, NO_MASK));
        mappings.add(mapping('W', VK_W, SHIFT_MASK));
        mappings.add(mapping('e', VK_E, NO_MASK));
        mappings.add(mapping('E', VK_E, SHIFT_MASK));
        mappings.add(mapping('€', VK_E, ALT_GRAPH_MASK));
        mappings.add(mapping('r', VK_R, NO_MASK));
        mappings.add(mapping('R', VK_R, SHIFT_MASK));
        mappings.add(mapping('t', VK_T, NO_MASK));
        mappings.add(mapping('T', VK_T, SHIFT_MASK));
        mappings.add(mapping('y', VK_Y, NO_MASK));
        mappings.add(mapping('Y', VK_Y, SHIFT_MASK));
        mappings.add(mapping('u', VK_U, NO_MASK));
        mappings.add(mapping('U', VK_U, SHIFT_MASK));
        mappings.add(mapping('i', VK_I, NO_MASK));
        mappings.add(mapping('I', VK_I, SHIFT_MASK));
        mappings.add(mapping('o', VK_O, NO_MASK));
        mappings.add(mapping('O', VK_O, SHIFT_MASK));
        mappings.add(mapping('p', VK_P, NO_MASK));
        mappings.add(mapping('P', VK_P, SHIFT_MASK));
        mappings.add(mapping('a', VK_A, NO_MASK));
        mappings.add(mapping('A', VK_A, SHIFT_MASK));
        mappings.add(mapping('s', VK_S, NO_MASK));
        mappings.add(mapping('S', VK_S, SHIFT_MASK));
        mappings.add(mapping('d', VK_D, NO_MASK));
        mappings.add(mapping('D', VK_D, SHIFT_MASK));
        mappings.add(mapping('f', VK_F, NO_MASK));
        mappings.add(mapping('F', VK_F, SHIFT_MASK));
        mappings.add(mapping('g', VK_G, NO_MASK));
        mappings.add(mapping('G', VK_G, SHIFT_MASK));
        mappings.add(mapping('h', VK_H, NO_MASK));
        mappings.add(mapping('H', VK_H, SHIFT_MASK));
        mappings.add(mapping('j', VK_J, NO_MASK));
        mappings.add(mapping('J', VK_J, SHIFT_MASK));
        mappings.add(mapping('k', VK_K, NO_MASK));
        mappings.add(mapping('K', VK_K, SHIFT_MASK));
        mappings.add(mapping('l', VK_L, NO_MASK));
        mappings.add(mapping('L', VK_L, SHIFT_MASK));
        mappings.add(mapping('\'', VK_QUOTE, NO_MASK));
        mappings.add(mapping('*', VK_QUOTE, SHIFT_MASK));
        mappings.add(mapping('<', VK_LESS, NO_MASK));
        mappings.add(mapping('>', VK_LESS, SHIFT_MASK));
        mappings.add(mapping('|', VK_LESS, ALT_GRAPH_MASK));
        mappings.add(mapping('z', VK_Z, NO_MASK));
        mappings.add(mapping('Z', VK_Z, SHIFT_MASK));
        mappings.add(mapping('x', VK_X, NO_MASK));
        mappings.add(mapping('X', VK_X, SHIFT_MASK));
        mappings.add(mapping('c', VK_C, NO_MASK));
        mappings.add(mapping('C', VK_C, SHIFT_MASK));
        mappings.add(mapping('v', VK_V, NO_MASK));
        mappings.add(mapping('V', VK_V, SHIFT_MASK));
        mappings.add(mapping('b', VK_B, NO_MASK));
        mappings.add(mapping('B', VK_B, SHIFT_MASK));
        mappings.add(mapping('n', VK_N, NO_MASK));
        mappings.add(mapping('N', VK_N, SHIFT_MASK));
        mappings.add(mapping('m', VK_M, NO_MASK));
        mappings.add(mapping('M', VK_M, SHIFT_MASK));
        mappings.add(mapping(',', VK_COMMA, NO_MASK));
        mappings.add(mapping(';', VK_COMMA, SHIFT_MASK));
        mappings.add(mapping('’', VK_COMMA, ALT_GRAPH_MASK));
        mappings.add(mapping('.', VK_PERIOD, NO_MASK));
        mappings.add(mapping(':', VK_PERIOD, SHIFT_MASK));
        mappings.add(mapping('_', VK_MINUS, SHIFT_MASK));
        mappings.add(mapping('–', VK_MINUS, ALT_GRAPH_MASK));
        mappings.add(mapping(' ', VK_SPACE, NO_MASK));
        return mappings;
    }
}
