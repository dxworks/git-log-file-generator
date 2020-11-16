package org.dxworks.logGenerator.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Threshold {
    private int hours;
    private int minutes;
    private int seconds;
}
