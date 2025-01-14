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

package com.splunk.opentelemetry.instrumentation.jvmmetrics;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.splunk.opentelemetry.testing.TestMetricsAccess;
import io.opentelemetry.instrumentation.testing.junit.AgentInstrumentationExtension;
import io.opentelemetry.instrumentation.testing.junit.InstrumentationExtension;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

@ExtendWith(AgentInstrumentationExtension.class)
class MicrometerJvmMetricsTest {
  @RegisterExtension
  static final InstrumentationExtension testing = AgentInstrumentationExtension.create();

  @Test
  void shouldRegisterJvmMeters() {
    Set<String> meterNames = TestMetricsAccess.getMeterNames();

    // classloader metrics
    assertTrue(meterNames.contains("jvm.classes.loaded"));
    // GC metrics
    assertTrue(meterNames.contains("jvm.gc.memory.allocated"));
    // GC pressure metrics
    assertTrue(meterNames.contains("jvm.gc.overhead"));
    // memory metrics
    assertTrue(meterNames.contains("jvm.memory.used"));
    // thread metrics
    assertTrue(meterNames.contains("jvm.threads.peak"));
    // allocated memory metrics
    assertTrue(meterNames.contains(AllocatedMemoryMetrics.METRIC_NAME));
    // Our custom GC metrics
    assertTrue(meterNames.contains(GcMemoryMetrics.METRIC_NAME));
  }
}
