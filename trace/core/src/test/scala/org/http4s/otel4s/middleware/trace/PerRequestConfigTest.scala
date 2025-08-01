/*
 * Copyright 2023 http4s.org
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

package org.http4s.otel4s.middleware.trace

import munit.FunSuite

class PerRequestConfigTest extends FunSuite {
  import PerRequestConfigTest._

  test("and") {
    assertEquals(PRC.Enabled.and(PRC.Enabled), PRC.Enabled)
    assertEquals(PRC.Enabled.and(PRC.Disabled), PRC.Disabled)
    assertEquals(PRC.Disabled.and(PRC.Enabled), PRC.Disabled)
    assertEquals(PRC.Disabled.and(PRC.Disabled), PRC.Disabled)
  }

  test("or") {
    assertEquals(PRC.Enabled.or(PRC.Enabled), PRC.Enabled)
    assertEquals(PRC.Enabled.or(PRC.Disabled), PRC.Enabled)
    assertEquals(PRC.Disabled.or(PRC.Enabled), PRC.Enabled)
    assertEquals(PRC.Disabled.or(PRC.Disabled), PRC.Disabled)
  }
}

object PerRequestConfigTest {
  val PRC: PerRequestConfig.type = PerRequestConfig
}
