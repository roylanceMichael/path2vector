package org.roylance.bezier

import org.junit.Test
import org.roylance.path.bezier.BezierPathUtility

class BezierPathUtilityTests {
    @Test
    fun simpleRunThrough() {
        // arrange
        val path = "M 318.682 435.328 C 336.773 468.038 390.252 415.718 331.356 399.257 260.096 379.339 178.065 457.144 84.3124 449.307 -27.6129 439.95 -35.3638 286.865 105.125 249.252 210.393 221.069 295.655 278.364 370.78 310.182 436.969 338.216 464.202 303.431 454.431 288.803 442.784 271.363 418.077 290.257 428.976 305.516 M 491.71 403.246 C 491.71 403.246 460.945 422.94 477.096 445.408 486.786 458.888 509.069 451.568 509.069 451.568 L 491.71 476.758 C 421.688 474.82 465.317 374.656 539.187 406.038 687.427 469.017 737.202 262.319 576.005 287.834 452.978 307.307 347.776 464.482 381.258 480.644 404.366 491.797 436.422 414.567 454.702 363.91 M 461.141 344.756 C 464.581 334.42 468.832 322.351 468.943 322.229 468.943 322.229 407.983 376.646 390.543 377.373 373.103 378.1 401.562 340.163 401.562 340.163 401.562 340.163 376.01 376.646 352.757 381.007 329.503 385.366 372.999 228.082 390.252 238.295 410.737 250.42 342.583 374.466 311.337 376.646 280.09 378.826 323.78 203.952 350.183 213.959 371.282 221.955 290.634 376.548 261.577 374.922 238.014 373.602 260.045 309.614 282.926 327.457 296.722 338.216 238.354 376.329 228.18 375.602 212.473 374.481 249.861 324.111 249.861 324.111 250.286 323.835 228.877 318.423 213.954 332.77 204.279 342.073 196.442 375.602 185.542 375.602 171.986 375.602 165.147 345.44 141.942 346.936 119.416 348.389 119.796 393.497 164.469 375.602 194.034 363.758 222.51 265.06 172.947 260.705 124.888 256.483 71.7094 425.63 70.976 426.123 M 488.039 520.995 C 505.436 505.825 503.209 537.576 536.28 545.559 564.378 552.341 586.258 520.837 627.167 535.77 677.255 554.051 645.558 631.395 562.326 630.426 502.323 629.728 472.032 617.185 386.525 585.712 263.623 540.478 121.377 524.204 77.3803 565.391 39.1581 601.17 78.9653 648.261 164.259 645.116 246.763 642.074 385.122 608.308 464.583 625.977 550.315 645.04 516.964 711.294 463.615 705.425 459.41 704.963 448.309 704.032 443.187 702.738 M 390.252 682.06 C 341.331 663.362 280.745 641.178 249.367 653.112 210.672 667.826 235.589 729.347 283.4 720.927 312.28 715.842 356.025 695.245 399.661 692.322 456.575 688.509 453.622 737.4 414.201 740.306 386.514 742.348 341.535 708.877 318.28 719.959 296.131 730.515 310.714 768.806 348.316 749.026 358.321 743.762 380.028 739.406 392.885 754.839 407.414 772.279 381.258 810.066 353.161 797.47 M 92.5751 507.11 C 73.1513 511.299 85.2221 561.76 123.647 530.698 173.916 490.062 219.689 379.385 213.358 376.388 205.173 372.514 190.956 399.077 177.897 431.047 M 177.146 434.384 C 158.68 482.992 143.662 545.28 165.399 536.983 211.702 519.306 294.835 391.506 269.312 383.655 254.66 379.149 199.049 528.319 194.139 537.059 194.139 537.059 235.954 456.017 272.585 473.673 289.736 481.941 223.892 542.62 245.491 543.136 269.767 543.716 302.441 485.005 302.441 485.005 302.441 485.005 268.398 550.073 296.49 538.05 314.634 530.284 348.692 481.339 349.246 481.028 L 327.243 537.323 C 325.198 533.063 361.131 477.2 382.672 491.202 397.206 500.649 348.519 549.335 378.901 538 389.356 534.101 396.9 520.832 412.401 508.879 M 377.191 621.923 C 364.664 641.676 355.681 661.859 359.943 676.088 371.849 715.847 462.605 502.828 462.605 502.828 432.085 539.889 404.291 548.493 402.292 531.168 398.699 500.022 454.515 493.115 454.515 493.115 M 488.039 520.995 C 476.006 537.072 499.741 559.012 522.141 542.382 546.938 523.974 509.151 481.341 509.151 481.341 509.151 481.341 502.258 501.328 475.372 519.495 460.307 529.674 418.975 565.548 390.252 601.17 M 503.096 236.29 C 543.789 262.643 600.324 222.192 602.621 173.361 605.489 112.406 546.938 72.7404 438.423 118.968 326.978 166.444 267.65 101.24 307.004 39.7985 346.721 -22.2108 436.969 -4.04379 426.796 41.7365 417.583 83.1935 371.569 66.4435 376.414 39.7985 M 307.004 181.257 C 290.975 199.465 305.5 225.109 337.659 211.292 394.41 186.909 458.27 70.6252 438.423 48.5822 428.903 38.0114 410.51 75.6925 397.195 117.977 M 393.377 132.015 C 380.788 177.898 374.176 227.009 389.495 226.309 419.498 224.94 508.157 70.9923 489.915 68.3024 484.033 67.4342 473.641 83.5392 464.785 102.713 M 462.023 110.699 C 442.596 156.931 421.717 224.795 421.688 224.857 421.688 224.857 447.959 175.672 468.589 188.363 484.204 197.969 446.224 221.321 463.129 229.96 480.569 238.874 519.475 219.59 513.831 200.15 507.291 177.623 461.837 213.696 503.096 236.29 M 125.467 144.481 C 144.552 100.867 177.707 55.2804 202.982 59.1764 254.309 67.0885 144.132 221.207 173.865 221.949 212.671 222.918 284.738 98.0685 263.516 86.8942 245.577 77.4484 195.231 221.949 220.333 221.949 249.026 221.949 307.004 122.303 294.835 115.372 277.944 105.751 242.35 192.345 260.147 213.23 281.462 238.243 311.498 193.852 276.618 189.007 M 123.736 149.997 C 106.811 193.327 102.744 236.005 131.769 229.216 M 169.774 207.907 C 169.774 207.907 154.763 141.477 109.525 141.477 64.2704 141.477 50.891 192.883 83.434 201.604 M 112.676 326.488 150.662 250.42 C 150.662 250.42 125.27 282.869 108.879 298.976 90.4223 317.114 75.8163 303.933 75.8163 285.451 M 89.3901 254.183 C 89.3901 254.183 18.9711 289.893 22.6874 360.491 26.5121 433.189 106.695 446.217 194.435 427.996 M 300.442 52.1183 C 300.442 52.1183 291.927 88.5822 333.701 112.456 394.055 146.949 510.765 57.7135 579.192 116.05 M 602.721 285.648 C 482.248 303.816 382.767 440.75 380.701 445.408 M 156.842 175.745 C 117.565 129.223 90.0502 152.423 86.1493 162.475 81.6274 174.129 95.5951 177.008 95.5951 177.008 L 83.434 201.604 M 516.861 671.505 C 523.644 557.176 205.786 615.018 168.02 616.279 109.992 618.216 72.1 596.901 94.3842 569.773 116.669 542.644 207.831 543.619 207.831 543.619 M 441.257 724.373 C 441.257 724.373 449.664 704.205 437.826 689.526 419.475 666.771 318.199 696.696 278.473 702.509 238.749 708.323 231.697 689.526 231.697 689.526 M 308.124 738.936 C 308.124 738.936 311.416 769.363 330.793 768.395 350.171 767.425 373.365 738.936 392.885 754.839"

        // act
        val vectors = BezierPathUtility.parsePathString(path)

        // assert
        println(vectors)
        assert(true)
    }

}