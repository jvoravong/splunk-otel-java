/*
 * Copyright Splunk Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.splunk.opentelemetry.instrumentation.jvmmetrics.micrometer;

import static com.splunk.opentelemetry.instrumentation.jvmmetrics.AllocatedMemoryMetrics.METRIC_NAME;

import com.splunk.opentelemetry.instrumentation.jvmmetrics.AllocatedMemoryMetrics;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.micrometer.core.instrument.binder.MeterBinder;

public class MicrometerAllocatedMemoryMetrics implements MeterBinder {
  private final AllocatedMemoryMetrics allocatedMemoryMetrics = new AllocatedMemoryMetrics();

  @Override
  public void bindTo(MeterRegistry registry) {
    if (!allocatedMemoryMetrics.isAvailable()) {
      return;
    }

    FunctionCounter.builder(
            METRIC_NAME,
            allocatedMemoryMetrics,
            AllocatedMemoryMetrics::getCumulativeAllocationTotal)
        .description("Approximate sum of heap allocations")
        .baseUnit(BaseUnits.BYTES)
        .tag("type", "heap")
        .register(registry);
  }
}