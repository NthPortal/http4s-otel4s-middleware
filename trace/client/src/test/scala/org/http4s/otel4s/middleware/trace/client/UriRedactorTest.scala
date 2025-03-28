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

package org.http4s.otel4s.middleware.trace.client

import munit.FunSuite
import org.http4s.Query
import org.http4s.Uri
import org.http4s.syntax.literals._

class UriRedactorTest extends FunSuite {
  test("UriRedactor.OnlyRedactUserInfo redacts username and password") {
    val redactor = new UriRedactor.OnlyRedactUserInfo {}

    val r1 = redactor.redactFull(uri"http://user:Password1@localhost/")
    assertEquals(r1, uri"http://REDACTED:REDACTED@localhost/")

    val r2 = redactor.redactFull(uri"http://user@localhost/")
    assertEquals(r2, uri"http://REDACTED@localhost/")
  }

  test("custom UriRedactor") {
    val redactor = new UriRedactor {
      override def redactAuthority(authority: Uri.Authority): Option[Uri.Authority] =
        None
      def redactPath(path: Uri.Path): Uri.Path =
        path"/REDACTED"
      def redactQuery(query: Query): Query =
        Query.fromMap(Map("REDACTED" -> Seq("REDACTED")))
      def redactFragment(fragment: Uri.Fragment): Option[Uri.Fragment] =
        Some("REDACTED")
    }

    val r1 = redactor.redactFull(uri"http://localhost/foo/bar?baz=qux&baz2=qux2#stuff")
    assertEquals(r1, uri"http:/REDACTED?REDACTED=REDACTED#REDACTED")

    val r2 = redactor.redactFull(uri"https:")
    assertEquals(r2, uri"https:/REDACTED?REDACTED=REDACTED")
  }
}
