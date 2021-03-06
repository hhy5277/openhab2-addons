/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.ihc.internal.converters;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.openhab.binding.ihc.internal.ws.exeptions.ConversionException;
import org.openhab.binding.ihc.internal.ws.resourcevalues.WSFloatingPointValue;

/**
 * DecimalType <-> WSFloatingPointValue converter.
 *
 * @author Pauli Anttila - Initial contribution
 */
public class DecimalTypeWSFloatingPointValueConverter implements Converter<WSFloatingPointValue, DecimalType> {

    @Override
    public DecimalType convertFromResourceValue(@NonNull WSFloatingPointValue from,
            @NonNull ConverterAdditionalInfo convertData) throws ConversionException {
        double d = from.getFloatingPointValue();
        BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);
        return new DecimalType(bd);
    }

    @Override
    public WSFloatingPointValue convertFromOHType(@NonNull DecimalType from, @NonNull WSFloatingPointValue value,
            @NonNull ConverterAdditionalInfo convertData) throws ConversionException {
        if (from.doubleValue() >= value.getMinimumValue() && from.doubleValue() <= value.getMaximumValue()) {
            value.setFloatingPointValue(from.doubleValue());
            return value;
        } else {
            throw new ConversionException("Value is not between acceptable limits (min=" + value.getMinimumValue()
                    + ", max=" + value.getMaximumValue() + ")");
        }
    }
}
