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

package org.http4s
package otel4s.middleware.trace.redact

/** Redacts a URI or request query. */
trait QueryRedactor {

  /** @return a redacted URI or request query, or `Query.empty` if the entire
    *         query is sensitive
    */
  def redactQuery(query: Query): Query
}

object QueryRedactor {

  /** A `QueryRedactor` that never redacts anything. */
  trait NeverRedact extends QueryRedactor {
    def redactQuery(query: Query): Query = query
  }
}
