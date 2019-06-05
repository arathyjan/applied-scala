package com.reagroup.appliedscala.urls.diagnostics

import cats.effect.IO
import cats.effect.ContextShift
import cats.effect.Timer
import com.reagroup.api.infrastructure.diagnostics.DiagnosticCheckDefinition
import com.reagroup.api.infrastructure.diagnostics.DiagnosticConfig
import com.reagroup.api.infrastructure.diagnostics.http4s.DiagnosticRoutes
import com.reagroup.appliedscala.config.Config
import com.reagroup.appliedscala.urls.repositories.PostgresqlRepository
import org.http4s.HttpRoutes

object Diagnostics {

  def apply(config: Config, pgsqlRepo: PostgresqlRepository)(implicit contextShift: ContextShift[IO], timer: Timer[IO]): HttpRoutes[IO] = {
    /*
     * Heartbeat checks are executed by the /diagnostic/status/heartbeat endpoint.
     *
     * This endpoint is the one we tell load balancers to use as a health check.
     * It answers the question: "Is this instance (or container) healthy,
     * or should it be terminated?" If the load balancer sees several
     * unhealthy responses (or can't get a response), it will tell the
     * auto-scaling group to terminate the instance and replace it with a new one.
     *
     * The heartbeat checks generally don't interact with external resources
     * (like databases, or other APIs), because you don't want to start
     * spawning new EC2 instances over and over again when the problem is
     * that the database is unreachable.
     *
     * It's not unusual for an API not to have any heartbeat checks at all.
     * Just being able to hit the heartbeat endpoint and get back a 200 response
     * is enough for a load balancer to determine whether or not this server is healthy.
     */
    val heartbeatChecks: Vector[DiagnosticCheckDefinition[IO]] = ???

    /*
     * The /diagnostic/status/diagnosis endpoint runs these checks and the heartbeat checks,
     * and returns a JSON object describing the outcome of each check.
     *
     * (There is also the /diagnostic/status/nagios endpoint. It runs the same checks,
     * but gives a less detailed plain text response.)
     *
     * Where the heartbeat endpoint is normally used for health checks, the diagnosis endpoint
     * is one we might only check manually when we suspect there is a problem.
     */
    val diagnosticChecks: Vector[DiagnosticCheckDefinition[IO]] = ???

    val diagnosticConfig = DiagnosticConfig(
      version = config.version,
      heartbeatChecks = heartbeatChecks,
      diagnosticChecks = diagnosticChecks
    )
    new DiagnosticRoutes(diagnosticConfig, Vector.empty, Map.empty).routes("/diagnostic")
  }

}
